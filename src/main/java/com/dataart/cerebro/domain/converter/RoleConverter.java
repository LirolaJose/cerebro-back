package com.dataart.cerebro.domain.converter;

import com.dataart.cerebro.domain.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Long> {

    @Override
    public Long convertToDatabaseColumn(Role role) {
        return role.getId();
    }

    @Override
    public Role convertToEntityAttribute(Long id) {
        return Role.getRoleById(id);
    }
}
