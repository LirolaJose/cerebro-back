package com.dataart.cerebro.configuration;

import com.dataart.cerebro.domain.ServiceDTO;
import org.springframework.format.Formatter;

import java.util.Locale;

public class ServiceDTOFormatter implements Formatter<ServiceDTO> {
    @Override
    public String print(ServiceDTO serviceDTO, Locale locale) {
        return serviceDTO.getId().toString();
    }

    @Override
    public ServiceDTO parse(String id, Locale locale) {
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(Integer.valueOf(id));
        return serviceDTO;
    }
}
