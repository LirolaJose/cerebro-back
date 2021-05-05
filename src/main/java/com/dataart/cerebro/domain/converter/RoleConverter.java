package com.dataart.cerebro.domain.converter;

import com.dataart.cerebro.domain.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Long> {

    @Override
    public Long convertToDatabaseColumn(Role role) {
        // FIXME: 5/5/2021 check null argument
        //         if (attribute == null) {
        //            return null;
        //        }
        return role.getId();
    }

    @Override
    public Role convertToEntityAttribute(Long id) {
        // FIXME: 5/5/2021 check null argument
        return Role.getRoleById(id);
    }
}
