package com.dataart.cerebro.dao;

import com.dataart.cerebro.domain.ServiceDTO;

import java.util.Set;

public interface ServiceDAO {
    Set<ServiceDTO> getAllServicesByCategoryId(int id);

    Set<ServiceDTO> getAllServicesByOrderId(int id);

    Double getTotalPriceServices(Set<ServiceDTO> servicesSet);
}
