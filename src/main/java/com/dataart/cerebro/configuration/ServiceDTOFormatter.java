package com.dataart.cerebro.configuration;

import com.dataart.cerebro.domain.AdditionalService;
import org.springframework.format.Formatter;

import java.util.Locale;

public class ServiceDTOFormatter implements Formatter<AdditionalService> {
    @Override
    public String print(AdditionalService additionalService, Locale locale) {
        return additionalService.getId().toString();
    }

    @Override
    public AdditionalService parse(String id, Locale locale) {
        AdditionalService additionalService = new AdditionalService();
        additionalService.setId(Long.valueOf(id));
        return additionalService;
    }
}
