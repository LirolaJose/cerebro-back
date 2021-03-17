package com.dataart.cerebro.controller;

import com.dataart.cerebro.service.AdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class AdvertisementController {
    final
    AdvertisementService advertisementService;

    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }


    @RequestMapping("/advertisements")
    public String getAdvertisementList(Model model) {
        model.addAttribute("advertisementList", advertisementService.getAllAdvertisements());
        return "advertisementList";
    }

    @RequestMapping("/advertisement")
    public String getAdvertisementById(@RequestParam("id") int id, Model model) {
        model.addAttribute("advertisement",  advertisementService.getAdvertisementById(id));
        return "advertisement";
    }
}
