package com.dataart.cerebro.controller;

import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.dao.CategoryDAO;
import com.dataart.cerebro.dao.StatusDAO;
import com.dataart.cerebro.dao.TypeDAO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import com.dataart.cerebro.dto.CategoryDTO;
import com.dataart.cerebro.dto.StatusDTO;
import com.dataart.cerebro.dto.TypeDTO;
import com.dataart.cerebro.service.AdvertisementService;
import com.dataart.cerebro.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(value = "/addAdvertisement", method = RequestMethod.GET)
    public String advertisementForm(Model model) {
        model.addAttribute("advertisement", new AdvertisementDTO());
        model.addAttribute("categorySet", categoryService.getAllCategory());
        model.addAttribute("typeEnum", TypeDTO.values());
        model.addAttribute("statusEnum", StatusDTO.values());
        return "addAdvertisement";
    }

    @RequestMapping(value = "/addAdvertisement", method = RequestMethod.POST)
    public String advertisementSubmit(@ModelAttribute  AdvertisementDTO advertisementDTO, Model model) {
        advertisementService.addAdvertisement(advertisementDTO.getTitle(), advertisementDTO.getText(), advertisementDTO.getPrice(),
                advertisementDTO.getAddress(),advertisementDTO.getCategoryDTO().getId(), advertisementDTO.getTypeDTO().getId(),
                advertisementDTO.getStatusDTO().getId());
        model.addAttribute("advertisementsList", advertisementService.getAllAdvertisements());
        return "redirect:/advertisementsList";
    }
}
