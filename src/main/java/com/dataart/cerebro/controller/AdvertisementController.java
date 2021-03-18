package com.dataart.cerebro.controller;

import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.dao.CategoryDAO;
import com.dataart.cerebro.dao.StatusDAO;
import com.dataart.cerebro.dao.TypeDAO;
import com.dataart.cerebro.dto.AdvertisementDTO;
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

    public AdvertisementController(AdvertisementService advertisementService, AdvertisementDAO advertisementDAO, CategoryDAO categoryDAO, TypeDAO typeDAO, StatusDAO statusDAO, CategoryService categoryService) {
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

//    @RequestMapping(value = "/addAdvertisement")
//    public String addAdvertisement(@RequestParam("title") String title, @RequestParam("text") String text, @RequestParam("price") Double price,
//                                   @RequestParam("address") String address, @RequestParam("categoryId") Integer categoryId,
//                                   @RequestParam("typeId") Integer typeId,
//                                   @RequestParam("statusId") Integer statusId) {
//
//        advertisementService.addAdvertisement(title, text, price, address, categoryId, typeId, statusId);
//        return "redirect:/advertisements";
//    }

    //test
    @RequestMapping(value = "/addAdvertisement", method = RequestMethod.GET)
    public String advertisementForm(Model model) {
        model.addAttribute("advertisement", new AdvertisementDTO());
        model.addAttribute("categorySet", categoryService.getAllCategory());
        model.addAttribute("typeEnum", TypeDTO.values());
        model.addAttribute("statusEnum", StatusDTO.values());
        return "addAdvertisement";
    }

    @RequestMapping (value = "/addAdvertisement", method = RequestMethod.POST)
    public String advertisementSubmit(@ModelAttribute AdvertisementDTO advertisementDTO, Model model) {
        advertisementService.addAdvertisement(advertisementDTO.getTitle(),advertisementDTO.getText(), advertisementDTO.getPrice(),
                advertisementDTO.getAddress(), advertisementDTO.getTypeDTO(),
                advertisementDTO.getStatusDTO());
        model.addAttribute("advertisementList", advertisementService.getAllAdvertisements());
        return "redirect:/advertisementsList";
    }
}
