package com.dataart.cerebro.configuration;

import com.dataart.cerebro.domain.Service;
import org.springframework.format.Formatter;

import java.util.Locale;

public class ServiceDTOFormatter implements Formatter<Service> {
    @Override
    public String print(Service service, Locale locale) {
        return service.getId().toString();
    }

    @Override
    public Service parse(String id, Locale locale) {
        Service service = new Service();
        service.setId(Long.valueOf(id));
        return service;
    }
}
