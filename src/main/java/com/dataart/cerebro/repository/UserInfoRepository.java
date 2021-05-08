package com.dataart.cerebro.repository;

import com.dataart.cerebro.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findUserInfoByEmail(String email);

    UserInfo findUserInfoById(Long userId);
}
