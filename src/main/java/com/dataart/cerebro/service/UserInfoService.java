package com.dataart.cerebro.service;

import com.dataart.cerebro.configuration.security.CustomUserDetails;
import com.dataart.cerebro.controller.dto.UserInfoDTO;
import com.dataart.cerebro.domain.Role;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.exception.*;
import com.dataart.cerebro.repository.UserInfoRepository;
import com.dataart.cerebro.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final CustomUserDetails customUserDetails;


    public UserInfo findUserInfoById(Long userInfoId) {
        log.info("Searching User by id {}", userInfoId);
        return userInfoRepository.findById(userInfoId).orElseThrow(() -> new NotFoundException("UserInfo", userInfoId));
    }

    public UserInfo findUserInfoByEmail(String email) {
        log.debug("Searching User by email {}", email);
        return userInfoRepository.findUserInfoByEmail(email);
    }

    public UserInfo findByEmailAndPassword(String email, String password) {
        UserInfo userInfo = findUserInfoByEmail(email);
        String encryptedPassword = encryptPassword(password);
        if (userInfo == null || !encryptedPassword.equals(userInfo.getPassword())) {
            throw new CredentialExpiredException("User has entered invalid credentials");
        }
        return userInfo;
    }

    @Transactional
    public void createNewUserInfo(UserInfoDTO userInfoDTO) {
        if (findUserInfoByEmail(userInfoDTO.getEmail()) != null) {
            log.info("An attempt to create user with existing email: {}", userInfoDTO.getEmail());
            throw new EmailExistsException(userInfoDTO.getEmail());
        }
        UserInfo userInfo = new UserInfo();
        log.info("Creating new USER");
        userInfo.setFirstName(userInfoDTO.getFirstName());
        userInfo.setSecondName(userInfoDTO.getSecondName());
        userInfo.setPhone(userInfoDTO.getPhone());
        userInfo.setEmail(userInfoDTO.getEmail());

        String encryptedPassword = encryptPassword(userInfoDTO.getPassword());
        userInfo.setPassword(encryptedPassword);
        userInfo.setRole(Role.USER);
        userInfo.setMoneyAmount(0.0);
        try {
            userInfoRepository.save(userInfo);
            log.info("Created new USER: {}, {}, {}, {}, {}", userInfo.getFirstName(), userInfo.getSecondName(),
                    userInfo.getPhone(), userInfo.getEmail(), userInfo.getRole());
        } catch (Exception e) {
            throw new DataProcessingException("Error during creating the new user", e);
        }
    }

    public String encryptPassword(String password) {
        return DigestUtils.md5Hex(password);
    }

    public UserInfo getCurrentUser() {
        return findUserInfoByEmail(SecurityUtils.getCurrentUserEmail());
    }

    @Transactional
    public void changeMoneyAmount(UserInfo customer, UserInfo owner, Double totalPrice) {
        if (customer.getMoneyAmount() < totalPrice) {
            throw new NotEnoughMoneyException(String.format("User (%s) doesn't have enough money to complete this order", customer.getEmail()));
        }
        customer.setMoneyAmount(customer.getMoneyAmount() - totalPrice);
        userInfoRepository.save(customer);
        owner.setMoneyAmount(owner.getMoneyAmount() + totalPrice);
        userInfoRepository.save(owner);
    }

    @Transactional
    public void increaseCurrentUserMoneyAmount(Double money, Long userId) {
        UserInfo userInfo = getCurrentUser();
        if (!userInfo.getId().equals(userId)) {
            throw new ValidationException("Incorrect user data");
        }
        userInfo.setMoneyAmount(userInfo.getMoneyAmount() + money);
        try {
            userInfoRepository.save(userInfo);
            log.info("User {} has successfully increased the amount of money", userInfo.getEmail());
        } catch (Exception e) {
            throw new DataProcessingException("Error during user balance update", e);
        }
    }
}
