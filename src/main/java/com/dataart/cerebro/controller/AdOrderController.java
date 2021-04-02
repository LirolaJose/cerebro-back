package com.dataart.cerebro.controller;

import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.dto.AdOrderDTO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;
import com.dataart.cerebro.exception.ContactInfoNullPointerException;
import com.dataart.cerebro.service.AdOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@Slf4j
public class AdOrderController {
    private final AdvertisementDAO advertisementDAO;
    private final AdOrderService adOrderService;

    public AdOrderController(AdvertisementDAO advertisementDAO, AdOrderService adOrderService) {
        this.advertisementDAO = advertisementDAO;
        this.adOrderService = adOrderService;
    }

    @GetMapping("/ordering")
    public String addAdOrderForm(@RequestParam("id") int id, Model model) {
        AdvertisementDTO advertisement = advertisementDAO.getAdvertisementById(id);
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
            log.info("Some parameters are not filled");
            throw new ContactInfoNullPointerException();
        }
        AdvertisementDTO advertisement = advertisementDAO.getAdvertisementById(adOrder.getAdvertisement().getId());
        adOrderService.adAdOrder(adOrder, advertisement, customer);
        return "redirect:/advertisementsList";
    }
}
