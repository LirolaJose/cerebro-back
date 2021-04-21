package com.dataart.cerebro.repository;

import com.dataart.cerebro.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findUserInfoByEmail(String email);

    UserInfo findUserInfoById(Long id);

    @Query(value = "SELECT email FROM user_Info;", nativeQuery = true)
    List<String> findEmail();
}
