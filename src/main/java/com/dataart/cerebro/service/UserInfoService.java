package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.Role;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.exception.EmailExistsException;
import com.dataart.cerebro.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfo findUserInfoById(Long id) {
        return userInfoRepository.findUserInfoById(id);
    }

    public UserInfo findUserInfoByEmail(String email) {
        return userInfoRepository.findUserInfoByEmail(email);
    }

    public void createNewUserInfo(UserInfo userInfo) {
        if (findUserInfoByEmail(userInfo.getEmail()) != null) {
            log.info("An attempt to create user with exists email: {}", userInfo.getEmail());
            throw new EmailExistsException(userInfo.getEmail());
        }
        String cryptPassword = DigestUtils.md5Hex(userInfo.getPassword());
        userInfo.setPassword(cryptPassword);
        userInfo.setRole(Role.USER);
        userInfoRepository.save(userInfo);
        log.info("Created new USER: {}, {}, {}, {}, {}", userInfo.getFirstName(), userInfo.getSecondName(),
                userInfo.getPhone(), userInfo.getEmail(), userInfo.getRole());
    }
}
