package com.dataart.cerebro;

import com.dataart.cerebro.configuration.model_mapper.AdvertisementMapper;
import com.dataart.cerebro.configuration.model_mapper.UserInfoMapper;
import com.dataart.cerebro.controller.dto.AdvertisementDTO;
import com.dataart.cerebro.controller.dto.UserInfoDTO;
import com.dataart.cerebro.domain.*;
import com.dataart.cerebro.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
class ModelMapperTests {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    AdvertisementMapper advertisementMapper;
    @Autowired
    CategoryService categoryService;

    @Test
    void whenConvertUserInfoDTOThenReturnUserInfo() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setFirstName("Model");
        userInfoDTO.setSecondName("Mapper");
        userInfoDTO.setPhone("89891777788");
        userInfoDTO.setEmail("user@email.com");
        userInfoDTO.setPassword("password");

        UserInfo userInfo = userInfoMapper.convertToUserInfo(userInfoDTO);

        assertThat(userInfo.getFirstName(), equalTo(userInfoDTO.getFirstName()));
        assertThat(userInfo.getSecondName(), equalTo(userInfoDTO.getSecondName()));
        assertThat(userInfo.getPhone(), equalTo(userInfoDTO.getPhone()));
        assertThat(userInfo.getEmail(), equalTo(userInfoDTO.getEmail()));
        assertThat(userInfo.getRole(), equalTo(Role.USER));
        assertThat(userInfo.getPassword(), equalTo(userInfoDTO.getPassword()));
    }

    @Test
    void whenConvertToUserDTOThenPasswordEqualNull() {
        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName("Model");
        userInfo.setSecondName("Mapper");
        userInfo.setPhone("89891777788");
        userInfo.setEmail("user@email.com");
        userInfo.setPassword("password");

        UserInfoDTO userInfoDTO = userInfoMapper.convertToUserInfoDTO(userInfo);

        assertNull(userInfoDTO.getPassword());
    }

    @Test
    void whenConvertToAdvertisementDTOThenOwnerIsDTO(){
        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName("Model");
        userInfo.setSecondName("Mapper");
        userInfo.setPhone("89891777788");
        userInfo.setEmail("user@email.com");
        userInfo.setPassword("password");


        Advertisement advertisement = new Advertisement();
        advertisement.setTitle("Mapper");
        advertisement.setPrice(100.0);
        advertisement.setCategory(categoryService.findCategoryById(1L));
        advertisement.setType(Type.SELL);
        advertisement.setStatus(Status.ACTIVE);
        advertisement.setOwner(userInfo);

        AdvertisementDTO advertisementDTO = advertisementMapper.convertToAdvertisementDTO(advertisement);

        assertThat(advertisementDTO.getTitle(), equalTo(advertisement.getTitle()));
        assertThat(advertisementDTO.getPrice(), equalTo(advertisement.getPrice()));
        assertThat(advertisementDTO.getCategory(), equalTo(advertisement.getCategory()));
        assertThat(advertisementDTO.getType(), equalTo(advertisement.getType()));
        assertThat(advertisementDTO.getStatus(), equalTo(advertisement.getStatus()));
        assertNull(advertisementDTO.getOwner().getPassword());
        assertThat(advertisementDTO.getOwner(), isA(UserInfoDTO.class));
    }
}
