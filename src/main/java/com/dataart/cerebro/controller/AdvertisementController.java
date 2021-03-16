package com.dataart.cerebro.controller;

import com.dataart.cerebro.dao.AdvertisementDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class AdvertisementController {
    final
    AdvertisementDAO advertisementService;

    public AdvertisementController(AdvertisementDAO advertisementDAO) {
        this.advertisementService = advertisementDAO;
    }

    @RequestMapping("/advertisements")
    public String advertisementList(Model model) {
        model.addAttribute("advertisementList", advertisementService.getAllAdvertisements());
        return "advertisementList";
    }

    @RequestMapping("/advertisement")
    public String advertisementById(@RequestParam("id") int id, Model model) {
        model.addAttribute("advertisement", advertisementService.getAdvertisementById(id));
        return "advertisement";
    }
}
