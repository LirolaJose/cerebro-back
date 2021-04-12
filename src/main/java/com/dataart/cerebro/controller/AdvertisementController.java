package com.dataart.cerebro.controller;

import com.dataart.cerebro.dao.ServiceDAO;
import com.dataart.cerebro.domain.AdvertisementDTO;
import com.dataart.cerebro.domain.ContactInfoDTO;
import com.dataart.cerebro.domain.Type;
import com.dataart.cerebro.exception.ValidationException;
import com.dataart.cerebro.service.AdvertisementService;
import com.dataart.cerebro.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class AdvertisementController {
    private final AdvertisementService advertisementService;
    private final CategoryService categoryService;

    public AdvertisementController(AdvertisementService advertisementService, CategoryService categoryService) {
        this.advertisementService = advertisementService;
        this.categoryService = categoryService;
    }


    @GetMapping("/advertisementsList")
    public String getAdvertisementList(Model model) {
        model.addAttribute("advertisementsList", advertisementService.getAllActiveAdvertisements());
        return "advertisementsList";
    }

    @GetMapping("/advertisement")
    public String getAdvertisementById(@RequestParam("id") int id, Model model) {
        model.addAttribute("advertisement", advertisementService.getAdvertisementById(id));
        return "advertisement";
    }

    @GetMapping(value = "/addAdvertisement")
    public String addAdvertisementForm(Model model) {
        model.addAttribute("advertisement", new AdvertisementDTO());
        model.addAttribute("categorySet", categoryService.getAllCategory());
        model.addAttribute("typeEnum", Type.values());
        model.addAttribute("contactInfo", new ContactInfoDTO());
        return "addAdvertisement";
    }

    @PostMapping(value = "/addAdvertisement")
    public String addAdvertisementSubmit(@ModelAttribute @Valid AdvertisementDTO advertisement, BindingResult bindingResultAds,
                                         @Valid ContactInfoDTO contactInfo, BindingResult bindingResultContact,
                                         MultipartFile file) throws IOException {

        if (bindingResultContact.hasErrors() || bindingResultAds.hasErrors()) {
            List<FieldError> fieldErrorList = new ArrayList<>();
            if (bindingResultContact.hasErrors()) {
                fieldErrorList.addAll(bindingResultContact.getFieldErrors());
            }
            if (bindingResultAds.hasErrors()) {
                fieldErrorList.addAll(bindingResultAds.getFieldErrors());
            }
            log.warn("Some parameters are not filled");
            throw new ValidationException(fieldErrorList);
        }
        byte[] image = file.getBytes();

        advertisementService.addAdvertisement(advertisement, contactInfo, image);
        return "redirect:/advertisementsList";
    }
}
