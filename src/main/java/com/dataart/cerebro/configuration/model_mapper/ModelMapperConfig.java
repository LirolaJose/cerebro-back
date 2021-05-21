package com.dataart.cerebro.configuration.model_mapper;

import com.dataart.cerebro.controller.dto.AdvertisementDTO;
import com.dataart.cerebro.controller.dto.NewAdvertisementDTO;
import com.dataart.cerebro.controller.dto.UserInfoDTO;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.domain.Role;
import com.dataart.cerebro.domain.Status;
import com.dataart.cerebro.domain.UserInfo;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
       ModelMapper modelMapper = new ModelMapper();

       modelMapper.createTypeMap(UserInfo.class, UserInfoDTO.class).addMappings(mapper -> mapper.skip(UserInfoDTO::setPassword));
       modelMapper.createTypeMap(UserInfoDTO.class, UserInfo.class).setPostConverter(mappingContext -> {
           mappingContext.getDestination().setRole(Role.USER);
           return mappingContext.getDestination();
       });

       modelMapper.createTypeMap(Advertisement.class, AdvertisementDTO.class)
                .setPostConverter(mappingContext -> {
                    mappingContext.getDestination().setOwner(modelMapper.map(mappingContext.getSource().getOwner(), UserInfoDTO.class));
                    return mappingContext.getDestination();
                });
       modelMapper.createTypeMap(NewAdvertisementDTO.class, Advertisement.class)
               .setPostConverter(mappingContext -> {
                   mappingContext.getDestination().setVisible(true);
                   mappingContext.getDestination().setStatus(Status.ACTIVE);
                   return mappingContext.getDestination();
               });

        return modelMapper;
    }
}
