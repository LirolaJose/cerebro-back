//package com.dataart.cerebro.controller.controllers;
//
//import com.dataart.cerebro.dao.ServiceRepository;
//import com.dataart.cerebro.domain.Service;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Set;
//
//@RestController
//public class ServiceController {
//    private final ServiceRepository serviceRepository;
//
//    public ServiceController(ServiceRepository serviceRepository) {
//        this.serviceRepository = serviceRepository;
//    }
//    @RequestMapping("category/services")
//    public Set<Service> getServicesByCategoryId(@RequestParam("id") Integer id){
//        return serviceRepository.getAllServicesByCategoryId(id);
//    }
//}
