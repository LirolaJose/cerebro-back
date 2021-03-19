package com.dataart.cerebro.controller;

import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.ContactInfoDTO;
import com.dataart.cerebro.dto.StatusDTO;
import com.dataart.cerebro.dto.TypeDTO;
import com.dataart.cerebro.service.AdvertisementService;
import com.dataart.cerebro.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class AdvertisementController {
    final AdvertisementService advertisementService;
    final CategoryService categoryService;

    public AdvertisementController(AdvertisementService advertisementService, CategoryService categoryService) {
        this.advertisementService = advertisementService;
        this.categoryService = categoryService;
    }


    @RequestMapping("/advertisementsList")
    public String getAdvertisementList(Model model) {
        model.addAttribute("advertisementsList", advertisementService.getAllAdvertisements());
        return "advertisementsList";
    }

    @RequestMapping("/advertisement")
    public String getAdvertisementById(@RequestParam("id") int id, Model model) {
        model.addAttribute("advertisement", advertisementService.getAdvertisementById(id));
        return "advertisement";
    }

    @GetMapping(value = "/addAdvertisement")
    public String addAdvertisementForm(Model model) {
        model.addAttribute("advertisement", new AdvertisementDTO());
        model.addAttribute("categorySet", categoryService.getAllCategory());
        model.addAttribute("typeEnum", TypeDTO.values());
        model.addAttribute("statusEnum", StatusDTO.values());
        model.addAttribute("contactInfo", new ContactInfoDTO());
        return "addAdvertisement";
    }

    @PostMapping(value = "/addAdvertisement")
    public String addAdvertisementSubmit(@ModelAttribute AdvertisementDTO advertisementDTO, ContactInfoDTO contactInfoDTO) {
        log.info("Creating new Advertisement, parameters: title: {}, text: {}, price: {}, address: {}, category: {}," +
                        "type: {}, status: {}", advertisementDTO.getTitle(), advertisementDTO.getText(), advertisementDTO.getPrice(),
                advertisementDTO.getAddress(), advertisementDTO.getCategoryDTO().getId(), advertisementDTO.getTypeDTO().getId(),
                advertisementDTO.getStatusDTO().getId());

        advertisementService.addAdvertisement(advertisementDTO, contactInfoDTO);
        return "redirect:/advertisementsList";
    }
}
