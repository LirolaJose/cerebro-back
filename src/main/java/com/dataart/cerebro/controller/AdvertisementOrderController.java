package com.dataart.cerebro.controller;

import com.dataart.cerebro.controller.dto.AdvertisementOrderDTO;
import com.dataart.cerebro.service.AdvertisementOrderService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@Api(tags = "Advertisement Order Controller")
public class AdvertisementOrderController {
    private final AdvertisementOrderService advertisementOrderService;

    public AdvertisementOrderController(AdvertisementOrderService advertisementOrderService) {
        this.advertisementOrderService = advertisementOrderService;
    }

    @PostMapping("/")
    public ResponseEntity<?> saveOrder(@RequestBody AdvertisementOrderDTO advertisementOrderDTO) {
        advertisementOrderService.createNewAdvertisementOrder(advertisementOrderDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
