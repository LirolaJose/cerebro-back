package com.dataart.cerebro.domain.converter;

import com.dataart.cerebro.domain.Type;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TypeConverter implements AttributeConverter<Type, Long> {

    @Override
    public Long convertToDatabaseColumn(Type type) {
        return type.getId();
    }

    @Override
    public Type convertToEntityAttribute(Long id) {
        return Type.getTypeById(id);
    }
}
