package com.dataart.cerebro.schedule;

import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.UserInfo;
import com.dataart.cerebro.email.EmailService;
import com.dataart.cerebro.repository.AdvertisementRepository;
import com.dataart.cerebro.service.AdvertisementService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class ScheduledEmailTasksTest {

    @Test
    void whenScheduledSendingExpiringLettersThenProperGroupingByEmail() {
        //given
        AdvertisementRepository advertisementRepository = mock(AdvertisementRepository.class);
        EmailService emailServiceMock = mock(EmailService.class);
        AdvertisementService advertisementService = new AdvertisementService(advertisementRepository, emailServiceMock,
                null, null, null, null);

        List<Advertisement> list = new ArrayList<>();

        Advertisement ad1 = new Advertisement();
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setEmail("email1");
        ad1.setOwner(userInfo1);

        Advertisement ad2 = new Advertisement();
        UserInfo userInfo2 = new UserInfo();
        userInfo2.setEmail("email2");
        ad2.setOwner(userInfo2);

        Advertisement ad3 = new Advertisement();
        UserInfo userInfo3 = new UserInfo();
        userInfo3.setEmail("email1");
        ad3.setOwner(userInfo3);

        list.add(ad1);
        list.add(ad2);
        list.add(ad3);

        when(advertisementRepository.findAdvertisementsByDate(1L, 1)).thenReturn(list);

        //when
        advertisementService.findAndNotifyByExpiringInDays( 1);

        //then
        Map<String, List<Advertisement>> expectedMap = new HashMap<>();
        expectedMap.put("email1", List.of(ad1, ad3));
        expectedMap.put("email2", List.of(ad2));

        verify(emailServiceMock).sendEmailAboutFinishingAdvertisement(expectedMap, "soon");
    }
}