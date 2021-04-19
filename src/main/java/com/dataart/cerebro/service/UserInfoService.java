package com.dataart.cerebro.service;

import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.repository.UserInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfo findUserInfoById(Long id){
        return userInfoRepository.findUserInfoById(id);
    }

    public UserInfo findUserInfoByEmail(String email){ return userInfoRepository.findUserInfoByEmail(email); }
}
