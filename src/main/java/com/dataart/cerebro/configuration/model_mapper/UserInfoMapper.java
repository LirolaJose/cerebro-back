package com.dataart.cerebro.configuration.model_mapper;

import com.dataart.cerebro.controller.dto.UserInfoDTO;
import com.dataart.cerebro.domain.UserInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserInfoMapper {
    private final ModelMapper modelMapper;

    public UserInfo convertToUserInfo(UserInfoDTO userInfoDTO) {
        return modelMapper.map(userInfoDTO, UserInfo.class);
    }

    public UserInfoDTO convertToUserInfoDTO(UserInfo userInfo) {
        return modelMapper.map(userInfo, UserInfoDTO.class);
    }
}
