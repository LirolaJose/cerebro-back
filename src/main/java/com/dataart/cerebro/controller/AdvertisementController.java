package com.dataart.cerebro.controller;

import com.dataart.cerebro.dao.AdvertisementDAO;
import com.dataart.cerebro.dto.AdvertisementDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class AdvertisementController {
    final
    AdvertisementDAO advertisementDAO;

    public AdvertisementController(AdvertisementDAO advertisementDAO) {
        this.advertisementDAO = advertisementDAO;
    }

    @RequestMapping("/advertisements")
    public List<AdvertisementDTO> advertisementDTOList() {
        log.info("Show all advertisement in the localhost:8080");
        return advertisementDAO.getAllAdvertisements();
    }

}
