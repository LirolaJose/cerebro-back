package com.dataart.cerebro.controller;

import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@Slf4j
public class AdvertisementController {
    final
    AdvertisementDAO advertisementDAO;

    public AdvertisementController(AdvertisementDAO advertisementDAO) {
        this.advertisementDAO = advertisementDAO;
    }

    @RequestMapping("/advertisements")
    public String advertisementList(Model model){
        model.addAttribute("adsList", advertisementDAO.getAllAdvertisements());
        return "advertisementList";
    }
    @RequestMapping("/advertisement")
    public String advertisementById(@RequestParam("id") int id, Model model){
        model.addAttribute("id", advertisementDAO.getAdvertisementById(id));
        return "advertisement";
    }

}
