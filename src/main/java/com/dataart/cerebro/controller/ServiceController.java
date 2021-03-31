package com.dataart.cerebro.controller;

import com.dataart.cerebro.dao.ServiceDAO;
import com.dataart.cerebro.dto.ServiceDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ServiceController {
    private final ServiceDAO serviceDAO;

    public ServiceController(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }
    @RequestMapping("category/services")
    public Set<ServiceDTO> getServicesByCategoryId(@RequestParam("id") Integer id){
        return serviceDAO.getAllServicesByCategoryId(id);
    }
}
