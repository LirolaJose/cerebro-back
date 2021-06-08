package com.dataart.cerebro.configuration.model_mapper;

import com.dataart.cerebro.controller.dto.AdvertisementDTO;
import com.dataart.cerebro.controller.dto.NewAdvertisementDTO;
import com.dataart.cerebro.controller.dto.UserInfoDTO;
import com.dataart.cerebro.domain.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
       ModelMapper modelMapper = new ModelMapper();

       modelMapper.createTypeMap(UserInfo.class, UserInfoDTO.class).addMappings(mapper -> mapper.skip(UserInfoDTO::setPassword));
       modelMapper.createTypeMap(NewAdvertisementDTO.class, Coordinates.class).addMappings(mapper -> mapper.skip(Coordinates::setAdvertisement));

       modelMapper.createTypeMap(UserInfoDTO.class, UserInfo.class).setPostConverter(mappingContext -> {
           mappingContext.getDestination().setRole(Role.USER);
           return mappingContext.getDestination();
       });

       modelMapper.createTypeMap(Advertisement.class, AdvertisementDTO.class)
                .setPostConverter(mappingContext -> {
                    mappingContext.getDestination().setOwner(modelMapper.map(mappingContext.getSource().getOwner(), UserInfoDTO.class));
                    if(mappingContext.getSource().getCoordinates() != null) {
                        mappingContext.getDestination().setLatitude(mappingContext.getSource().getCoordinates().getLatitude());
                        mappingContext.getDestination().setLongitude(mappingContext.getSource().getCoordinates().getLongitude());
                    }
                    return mappingContext.getDestination();
                });

       modelMapper.createTypeMap(NewAdvertisementDTO.class, Advertisement.class)
               .setPostConverter(mappingContext -> {
                   mappingContext.getDestination().setVisible(true);
                   mappingContext.getDestination().setStatus(Status.ACTIVE);
                   mappingContext.getDestination().setId(null);
                   return mappingContext.getDestination();
               });

        return modelMapper;
    }
}
