package com.dataart.cerebro.controller;

import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;
import com.dataart.cerebro.service.AdvertisementService;
import com.dataart.cerebro.service.ContactInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class ContactInfoController {
    final ContactInfoService contactInfoService;
    final AdvertisementService advertisementService;

    public ContactInfoController(ContactInfoService contactInfoService, AdvertisementService advertisementService) {
        this.contactInfoService = contactInfoService;
        this.advertisementService = advertisementService;
    }

    @RequestMapping("/contactsList")
    public String getAllContacts(Model model){
        model.addAttribute("contactsList", contactInfoService.getAllContactsInfo());
        return "contactsList";
    }

//    @GetMapping("/addContactInfo")
//    public String addContactForm(Model model) {
//        model.addAttribute("contact", new ContactInfoDTO());
//        return "addContactInfo";
//    }
//    @PostMapping("/addContactInfo")
//    public String addContactSubmit(@ModelAttribute ContactInfoDTO contactInfoDTO, Model model){
//        log.info("Creating new Contact Info, parameters: name: {}, phone: {}, email: {}",
//                contactInfoDTO.getName(), contactInfoDTO.getPhone(), contactInfoDTO.getEmail());
//
//        contactInfoService.addContactInfo(contactInfoDTO.getName(), contactInfoDTO.getPhone(), contactInfoDTO.getEmail());
//
////        advertisementService.addAdvertisement(advertisementDTO.getTitle(), advertisementDTO.getText(), advertisementDTO.getPrice(),
////                advertisementDTO.getAddress(), advertisementDTO.getCategoryDTO().getId(), advertisementDTO.getTypeDTO().getId(),
////                advertisementDTO.getStatusDTO().getId(), contactInfoDTO.getId());
//
//        model.addAttribute("contactsList", contactInfoService.getAllContactsInfo());
//        return "redirect:/contactsList";
//    }
}
