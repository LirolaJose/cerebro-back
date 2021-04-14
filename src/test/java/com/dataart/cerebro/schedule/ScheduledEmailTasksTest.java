//package com.dataart.cerebro.schedule;
//
//import com.dataart.cerebro.dao.AdvertisementRepository;
//import com.dataart.cerebro.domain.ContactInfo;
//import com.dataart.cerebro.email.EmailService;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static java.util.stream.Collectors.groupingBy;
//import static org.mockito.Mockito.*;
//
//public class ScheduledEmailTasksTest {
//
//    @Test
//    void whenScheduledSendingExpiringLettersThenProperGroupingByEmail() {
//        //given
//        AdvertisementRepository advertisementRepositoryMock = mock(AdvertisementRepository.class);
//        EmailService emailServiceMock = mock(EmailService.class);
//        ScheduledEmailTasks scheduledEmailTasks = new ScheduledEmailTasks(advertisementRepositoryMock, emailServiceMock);
//        List<com.dataart.cerebro.domain.Advertisement> list = new ArrayList<>();
//
//        com.dataart.cerebro.domain.Advertisement ad1 = new com.dataart.cerebro.domain.Advertisement();
//        ContactInfo contactInfo1 = new ContactInfo();
//        contactInfo1.setEmail("email1");
//        ad1.setOwner(contactInfo1);
//
//        com.dataart.cerebro.domain.Advertisement ad2 = new com.dataart.cerebro.domain.Advertisement();
//        ContactInfo contactInfo2 = new ContactInfo();
//        contactInfo2.setEmail("email2");
//        ad2.setOwner(contactInfo2);
//
//        com.dataart.cerebro.domain.Advertisement ad3 = new com.dataart.cerebro.domain.Advertisement();
//        ContactInfo contactInfo3 = new ContactInfo();
//        contactInfo3.setEmail("email1");
//        ad3.setOwner(contactInfo3);
//
//        list.add(ad1);
//        list.add(ad2);
//        list.add(ad3);
//
//        when(advertisementRepositoryMock.getExpiringAdvertisements()).thenReturn(list);
//
//        //when
//        scheduledEmailTasks.findExpiringAdvertisements();
//
//        //then
//        Map<String, List<com.dataart.cerebro.domain.Advertisement>> expectedMap = new HashMap<>();
//        expectedMap.put("email1", List.of(ad1, ad3));
//        expectedMap.put("email2", List.of(ad2));
//
//        verify(emailServiceMock).sendEmailAboutExpiring(expectedMap);
//    }
//}