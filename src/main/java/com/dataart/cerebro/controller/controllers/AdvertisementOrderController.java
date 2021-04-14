//package com.dataart.cerebro.controller.controllers;
//
//import com.dataart.cerebro.domain.AdvertisementOrder;
//import com.dataart.cerebro.domain.Advertisement;
//import com.dataart.cerebro.domain.ContactInfo;
//import com.dataart.cerebro.exception.ValidationException;
//import com.dataart.cerebro.service.AdvertisementOrderService;
//import com.dataart.cerebro.service.AdvertisementService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.validation.Valid;
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//@Slf4j
//public class AdvertisementOrderController {
//    private final AdvertisementOrderService advertisementOrderService;
//    private final AdvertisementService advertisementService;
//
//    public AdvertisementOrderController(AdvertisementOrderService advertisementOrderService, AdvertisementService advertisementService) {
//        this.advertisementOrderService = advertisementOrderService;
//        this.advertisementService = advertisementService;
//    }
//
//    @GetMapping("/ordering")
//    public String addAdOrderForm(@RequestParam("id") long id, Model model) {
//        Advertisement advertisement = advertisementService.getAdvertisementById(id);
//        AdvertisementOrder adOrder = new AdvertisementOrder();
//
//        adOrder.setAdvertisement(advertisement);
//        model.addAttribute("advertisement", advertisement);
//        model.addAttribute("order", adOrder);
//        model.addAttribute("customer", new ContactInfo());
//        model.addAttribute("services", advertisement.getCategory().getServices());
//        return "ordering";
//    }
//
//    @PostMapping("/ordering")
//    public String addAdOrderSubmit(@ModelAttribute("order") AdvertisementOrder adOrder,
//                                   @Valid ContactInfo customer, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            List<FieldError> fieldErrorList = new ArrayList<>(bindingResult.getFieldErrors());
//            log.warn("Some parameters are not filled");
//            throw new ValidationException(fieldErrorList);
//        }
//        Advertisement advertisement = advertisementService.getAdvertisementById(adOrder.getAdvertisement().getId());
//        advertisementOrderService.addAdOrder(adOrder, advertisement, customer);
//        return "redirect:/advertisementsList";
//    }
//}
