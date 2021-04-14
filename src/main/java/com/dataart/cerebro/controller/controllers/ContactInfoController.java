//package com.dataart.cerebro.controller.controllers;
//
//import com.dataart.cerebro.service.ContactInfoService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@Slf4j
//public class ContactInfoController {
//    private final ContactInfoService contactInfoService;
//
//    public ContactInfoController(ContactInfoService contactInfoService) {
//        this.contactInfoService = contactInfoService;
//    }
//
//    @RequestMapping("/contactsList")
//    public String getAllContacts(Model model) {
//        model.addAttribute("contactsList", contactInfoService.getAllContactsInfo());
//        return "contactsList";
//    }
//}
