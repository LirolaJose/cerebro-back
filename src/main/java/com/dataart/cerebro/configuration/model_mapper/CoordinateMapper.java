package com.dataart.cerebro.configuration.model_mapper;

import com.dataart.cerebro.controller.dto.CoordinatesDTO;
import com.dataart.cerebro.domain.Coordinates;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CoordinateMapper {
    private final ModelMapper modelMapper;

    public Coordinates convertToCoordinates(CoordinatesDTO coordinatesDTO) {
        return modelMapper.map(coordinatesDTO, Coordinates.class);
    }
}
