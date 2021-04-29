package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.Role;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.exception.EmailExistsException;
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

    public UserInfo findUserInfoById(Long id) {
        log.info("Searching User by id {}", id);
        UserInfo userInfo = userInfoRepository.findUserInfoById(id);
        if (userInfo == null) {
            log.info("User with id {} not found", id);
            throw new NotFoundException("User", id);
        }
        return userInfo;
    }

    public UserInfo findUserInfoByEmail(String email) {
        log.info("Searching User by email {}", email);
        return userInfoRepository.findUserInfoByEmail(email);
    }

    @Transactional
    public void createNewUserInfo(UserInfo userInfo) {
        if (findUserInfoByEmail(userInfo.getEmail()) != null) {
            log.info("An attempt to create user with exists email: {}", userInfo.getEmail());
            throw new EmailExistsException(userInfo.getEmail());
        }
        log.info("Creating new USER");
        String encryptedPassword = encryptPassword(userInfo.getPassword());
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
        }
    }
}