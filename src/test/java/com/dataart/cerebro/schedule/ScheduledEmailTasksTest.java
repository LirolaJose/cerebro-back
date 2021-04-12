package com.dataart.cerebro.schedule;

import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.domain.AdvertisementDTO;
import com.dataart.cerebro.domain.ContactInfoDTO;
import com.dataart.cerebro.email.EmailService;
import org.hibernate.annotations.Immutable;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ScheduledEmailTasksTest {

    @Test
    void whenScheduledSendingExpiringLettersThenProperGroupingByEmail() {
        //given
        AdvertisementDAO advertisementDAOMock = mock(AdvertisementDAO.class);
        EmailService emailServiceMock = mock(EmailService.class);
        ScheduledEmailTasks scheduledEmailTasks = new ScheduledEmailTasks(advertisementDAOMock, emailServiceMock);
        List<AdvertisementDTO> list = new ArrayList<>();

        AdvertisementDTO ad1 = new AdvertisementDTO();
        ContactInfoDTO contactInfo1 = new ContactInfoDTO();
        contactInfo1.setEmail("email1");
        ad1.setOwner(contactInfo1);

        AdvertisementDTO ad2 = new AdvertisementDTO();
        ContactInfoDTO contactInfo2 = new ContactInfoDTO();
        contactInfo2.setEmail("email2");
        ad2.setOwner(contactInfo2);

        AdvertisementDTO ad3 = new AdvertisementDTO();
        ContactInfoDTO contactInfo3 = new ContactInfoDTO();
        contactInfo3.setEmail("email1");
        ad3.setOwner(contactInfo3);

        list.add(ad1);
        list.add(ad2);
        list.add(ad3);

        when(advertisementDAOMock.getExpiringAdvertisements()).thenReturn(list);

        //when
        scheduledEmailTasks.findExpiringAdvertisements();

        //then
        Map<String, List<AdvertisementDTO>> expectedMap = new HashMap<>();
        expectedMap.put("email1", List.of(ad1, ad3));
        expectedMap.put("email2", List.of(ad2));

        verify(emailServiceMock).sendEmailAboutExpiring(expectedMap);
    }
}