package com.dataart.cerebro.controller;

import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.service.AdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class AdvertisementController {
    final AdvertisementService advertisementService;
    final AdvertisementDAO advertisementDAO;

    public AdvertisementController(AdvertisementService advertisementService, AdvertisementDAO advertisementDAO) {
        this.advertisementService = advertisementService;
        this.advertisementDAO = advertisementDAO;
    }


    @RequestMapping("/advertisements")
    public String getAdvertisementList(Model model) {
        model.addAttribute("advertisementList", advertisementService.getAllAdvertisements());
        return "advertisementList";
    }

    @RequestMapping("/advertisement")
    public String getAdvertisementById(@RequestParam("id") int id, Model model) {
        model.addAttribute("advertisement", advertisementService.getAdvertisementById(id));
        return "advertisement";
    }

    @RequestMapping(value = "/addAdvertisement")
    public String addAdvertisement(@RequestParam("title") String title, @RequestParam("text") String text, @RequestParam("price") Double price,
                                   @RequestParam("address") String address, @RequestParam("categoryId") Integer categoryId,
                                   @RequestParam("typeId") Integer typeId,
                                   @RequestParam("statusId") Integer statusId) {

        advertisementDAO.addAdvertisement(title, text, price, address, categoryId, typeId, statusId);
        return "redirect:/advertisements";
    }
}
