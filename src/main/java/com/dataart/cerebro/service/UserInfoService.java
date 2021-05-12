package com.dataart.cerebro.service;

import com.dataart.cerebro.controller.dto.UserInfoDTO;
import com.dataart.cerebro.domain.Role;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.exception.EmailExistsException;
import com.dataart.cerebro.exception.NotEnoughMoneyException;
import com.dataart.cerebro.exception.NotFoundException;
import com.dataart.cerebro.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfo findUserInfoById(Long userInfoId) {
        log.info("Searching User by id {}", userInfoId);
        UserInfo userInfo = userInfoRepository.findUserInfoById(userInfoId);
        if (userInfo == null) {
            log.warn("User with id {} not found", userInfoId);
            throw new NotFoundException("User", userInfoId);
        }
        return userInfo;
    }

    public UserInfo findUserInfoByEmail(String email) {
        log.info("Searching User by email {}", email);
        return userInfoRepository.findUserInfoByEmail(email);
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
        userInfoRepository.save(userInfo);
        log.info("Created new USER: {}, {}, {}, {}, {}", userInfo.getFirstName(), userInfo.getSecondName(),
                userInfo.getPhone(), userInfo.getEmail(), userInfo.getRole());
    }

    public String encryptPassword(String password) {
        return DigestUtils.md5Hex(password);
    }

    public UserInfo getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return findUserInfoByEmail(authentication.getName());
    }

    @Transactional
    public void changeMoneyAmount(UserInfo customer, UserInfo owner, Double totalPrice) {
        if (customer.getMoneyAmount() >= totalPrice) {
            customer.setMoneyAmount(customer.getMoneyAmount() - totalPrice);
            userInfoRepository.save(customer);
            owner.setMoneyAmount(owner.getMoneyAmount() + totalPrice);
            userInfoRepository.save(owner);
        } else{
            throw new NotEnoughMoneyException(String.format("User (%s) doesn't have enough money to complete this order", customer.getEmail()));
        }
    }
}
