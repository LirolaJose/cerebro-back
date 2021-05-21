package com.dataart.cerebro.configuration.model_mapper;

import com.dataart.cerebro.controller.dto.AdvertisementDTO;
import com.dataart.cerebro.controller.dto.NewAdvertisementDTO;
import com.dataart.cerebro.domain.Advertisement;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdvertisementMapper {
    private final ModelMapper modelMapper;

    public Advertisement convertToAdvertisement(NewAdvertisementDTO newAdvertisementDTO) {
        return modelMapper.map(newAdvertisementDTO, Advertisement.class);
    }

    public AdvertisementDTO convertToAdvertisementDTO(Advertisement advertisement) {
        return modelMapper.map(advertisement, AdvertisementDTO.class);
    }
}