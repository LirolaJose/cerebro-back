package com.dataart.cerebro.controller;

import com.dataart.cerebro.domain.AdOrderDTO;
import com.dataart.cerebro.domain.AdvertisementDTO;
import com.dataart.cerebro.domain.ContactInfoDTO;
import com.dataart.cerebro.exception.ValidationException;
import com.dataart.cerebro.service.AdOrderService;
import com.dataart.cerebro.service.AdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class AdOrderController {
    private final AdOrderService adOrderService;
    private final AdvertisementService advertisementService;

    public AdOrderController(AdOrderService adOrderService, AdvertisementService advertisementService) {
        this.adOrderService = adOrderService;
        this.advertisementService = advertisementService;
    }

    @GetMapping("/ordering")
    public String addAdOrderForm(@RequestParam("id") int id, Model model) {
        AdvertisementDTO advertisement = advertisementService.getAdvertisementById(id);
        AdOrderDTO adOrder = new AdOrderDTO();

        adOrder.setAdvertisement(advertisement);
        model.addAttribute("advertisement", advertisement);
        model.addAttribute("order", adOrder);
        model.addAttribute("customer", new ContactInfoDTO());
        model.addAttribute("services", advertisement.getCategory().getServicesSet());
        return "ordering";
    }

    @PostMapping("/ordering")
    public String addAdOrderSubmit(@ModelAttribute("order") AdOrderDTO adOrder,
                                   @Valid ContactInfoDTO customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrorList = new ArrayList<>(bindingResult.getFieldErrors());
            log.warn("Some parameters are not filled");
            throw new ValidationException(fieldErrorList);
        }
        AdvertisementDTO advertisement = advertisementService.getAdvertisementById(adOrder.getAdvertisement().getId());
        adOrderService.addAdOrder(adOrder, advertisement, customer);
        return "redirect:/advertisementsList";
    }
}
