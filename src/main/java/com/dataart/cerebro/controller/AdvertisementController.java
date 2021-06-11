package com.dataart.cerebro.controller;

import com.dataart.cerebro.configuration.model_mapper.AdvertisementMapper;
import com.dataart.cerebro.controller.dto.AdvertisementDTO;
import com.dataart.cerebro.controller.dto.CoordinatesDTO;
import com.dataart.cerebro.controller.dto.NewAdvertisementDTO;
import com.dataart.cerebro.domain.Advertisement;
import com.dataart.cerebro.exception.ValidationException;
import com.dataart.cerebro.service.AdvertisementService;
import com.dataart.cerebro.service.ImageService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/advertisement")
@Api(tags = "Advertisement")
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementService advertisementService;
    private final ImageService imageService;
    private final AdvertisementMapper advertisementMapper;


    @GetMapping
    public ResponseEntity<?> getActiveAdvertisements(@PageableDefault(size = 5) Pageable pageable) {
        Page<Advertisement> advertisements = advertisementService.findActiveAdvertisements(pageable);
        List<AdvertisementDTO> advertisementDTOS = new ArrayList<>();
        advertisements.forEach(advertisement -> advertisementDTOS.add(advertisementMapper.convertToAdvertisementDTO(advertisement)));

        return ResponseEntity.ok(new PageImpl<>(advertisementDTOS, advertisements.getPageable(),
                advertisements.getTotalElements()));
    }

    @GetMapping("/{advertisementId}")
    public ResponseEntity<?> getAdvertisementById(@PathVariable Long advertisementId) {
        Advertisement advertisement = advertisementService.findAdvertisementById(advertisementId);
        AdvertisementDTO advertisementDTO = advertisementMapper.convertToAdvertisementDTO(advertisement);
        return ResponseEntity.ok(advertisementDTO);
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<?> getAdvertisementImageByImageId(@PathVariable Long imageId) {
        byte[] imageBytes = imageService.findImageById(imageId);
        return ResponseEntity.ok(imageBytes);
    }


    @Secured("ROLE_USER")
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveAdvertisement(@RequestPart("advertisementDTO") @Valid NewAdvertisementDTO newAdvertisementDTO,
                                               BindingResult bindingResult,
                                               @RequestPart(value = "coordinatesDTO", required = false) CoordinatesDTO coordinatesDTO,
                                               @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            throw new ValidationException(fieldErrorList);
        }
        if (images != null) {
            for (MultipartFile image : images) {
                String contentType = image.getContentType();
                if (contentType != null && !isSupportedContentType(contentType)) {
                    throw new ValidationException("Only PNG or JPG images are allowed");
                }
            }
        }
        advertisementService.createNewAdvertisement(newAdvertisementDTO, images, coordinatesDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("static/image/png")
                || contentType.equals("static/image/jpg")
                || contentType.equals("static/image/jpeg");
    }
}
