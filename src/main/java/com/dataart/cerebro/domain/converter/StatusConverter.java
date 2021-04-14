package com.dataart.cerebro.domain.converter;

import com.dataart.cerebro.domain.Status;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, Long> {

    @Override
    public Long convertToDatabaseColumn(Status status) {
        return status.getId();
    }

    @Override
    public Status convertToEntityAttribute(Long id) {
        return Status.getStatusById(id);
    }

}
