package com.dataart.cerebro.controller;

import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;
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

    public ContactInfoController(ContactInfoService contactInfoService) {
        this.contactInfoService = contactInfoService;
    }

    @RequestMapping("/contactsList")
    public String getAllContacts(Model model){
        model.addAttribute("contactsList", contactInfoService.getAllContactsInfo());
        return "contactsList";
    }

    @GetMapping("/addContactInfo")
    public String addContactForm(Model model) {
        model.addAttribute("contact", new ContactInfoDTO());
        return "addContactInfo";
    }
    @PostMapping("/addContactInfo")
    public String addContactSubmit(@ModelAttribute ContactInfoDTO contactInfoDTO, Model model){
        contactInfoService.addContactInfo(contactInfoDTO.getName(), contactInfoDTO.getPhone(), contactInfoDTO.getEmail());
        model.addAttribute("contactsList", contactInfoService.getAllContactsInfo());
        return "redirect:/contactsList";
    }
}
