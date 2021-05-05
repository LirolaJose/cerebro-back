package com.dataart.cerebro;


import com.dataart.cerebro.controller.ControllerExceptionHandler;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.repository.UserInfoRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
class IntegrationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UserInfoRepository userInfoRepository;

    @AfterEach
    void resetUserInfoData() {
        userInfoRepository.deleteAll();
    }


    @Test
    void whenEnterToMainPageThenGetStatus200() {
        ResponseEntity<String> response = restTemplate.
                getForEntity("http://localhost:8080/api/advertisement/", String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void whenNewUserRegisteredThenGet201StatusAndLogin() {
        registerNewUser();
    }

    @Test
    void whenLoginThenCreatedUserAndLogged() {
        UserInfo userInfo = registerNewUser();
        String securedUrl = "/advertisementForm.html";
        String cookie = getCookieForUser(userInfo.getEmail(), userInfo.getPassword(), "/login");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie);

        ResponseEntity<String> responseFromSecuredEndPoint = restTemplate.exchange(securedUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        assertThat(responseFromSecuredEndPoint.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void whenAllRequiredFieldsAreNotFilledThenGet400StatusAndNotFilledFields() {
        UserInfo userInfo = new UserInfo();
        ResponseEntity<?> response = restTemplate.postForEntity("/registration/", userInfo, ControllerExceptionHandler.ErrorDTO.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));

        String[] exceptedFields = {"firstName", "secondName", "email", "phone", "password"};
        Arrays.stream(exceptedFields).forEach(field -> {
            assertTrue(((ControllerExceptionHandler.ErrorDTO) response.getBody()).getMessage().contains(field));
        });
    }

    @Test
    void whenTryToEnterToSecuredPageThenGetStatus302() {
        String securedUrl = "/advertisementForm.html";
        String cookie = getCookieForUser("user@gmail.com", "incorrect password", "/login");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie);

        ResponseEntity<String> responseFromSecuredEndPoint = restTemplate.exchange(securedUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        assertThat(responseFromSecuredEndPoint.getStatusCode(), equalTo(HttpStatus.FOUND));

    }

    private String getCookieForUser(String username, String password, String loginUrl) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.set("username", username);
        form.set("password", password);
        ResponseEntity<String> loginResponse = restTemplate.postForEntity(
                loginUrl,
                new HttpEntity<>(form, new HttpHeaders()),
                String.class);
        return loginResponse.getHeaders().get("Set-Cookie").get(0);
    }

    private UserInfo registerNewUser() {
        UserInfo userInfo = new UserInfo();
        Faker faker = new Faker();

        userInfo.setFirstName(faker.name().firstName());

        String secondName = faker.name().lastName();
        userInfo.setSecondName(secondName);

        userInfo.setPhone(faker.phoneNumber().cellPhone());
        userInfo.setPassword("password");

        String email = faker.bothify("????##@gmail.com");
        userInfo.setEmail(email);

        ResponseEntity<UserInfo> response = restTemplate.postForEntity("/registration/", userInfo, UserInfo.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));

        assertThat(userInfoRepository.findUserInfoByEmail(email).getSecondName(), equalTo(email));
        return userInfo;
    }

}
