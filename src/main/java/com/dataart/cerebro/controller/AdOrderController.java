package com.dataart.cerebro.controller;

import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.dto.AdOrderDTO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;
import com.dataart.cerebro.dto.ServiceDTO;
import com.dataart.cerebro.exception.ContactInfoNullPointerException;
import com.dataart.cerebro.service.AdOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        AdvertisementDTO advertisementDTO = advertisementDAO.getAdvertisementById(id);
        AdOrderDTO adOrderDTO = new AdOrderDTO();

        adOrderDTO.setAdvertisementDTO(advertisementDTO);
        model.addAttribute("advertisement", advertisementDTO);
        model.addAttribute("order", adOrderDTO);
        model.addAttribute("customer", new ContactInfoDTO());
        model.addAttribute("services", advertisementDTO.getCategoryDTO().getServicesSet());
        return "ordering";
    }

    @PostMapping("/ordering")
    public String addAdOrderSubmit(@ModelAttribute ("order") AdOrderDTO adOrderDTO,
                                   @Valid ContactInfoDTO customer, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.info("Some parameters are not filled");
            throw new ContactInfoNullPointerException();
        }
        AdvertisementDTO advertisementDTO = advertisementDAO.getAdvertisementById(adOrderDTO.getAdvertisementDTO().getId());
        adOrderService.adAdOrder(adOrderDTO, advertisementDTO, customer);
        return "redirect:/advertisementsList";
    }
}
