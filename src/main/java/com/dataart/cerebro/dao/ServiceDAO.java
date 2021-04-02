package com.dataart.cerebro.dao;

import com.dataart.cerebro.dto.ServiceDTO;

import java.util.Set;

public interface ServiceDAO {
    Set<ServiceDTO> getAllServicesByCategoryId(int id);

    Set<ServiceDTO> getAllServicesByOrderId(int id);

    ServiceDTO getServiceById(int id);

    Double getTotalPriceServices(Set<ServiceDTO> servicesSet);
}
