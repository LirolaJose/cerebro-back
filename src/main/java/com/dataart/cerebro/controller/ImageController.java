package com.dataart.cerebro.controller;

import com.dataart.cerebro.dao.AdvertisementDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ImageController {
    final
    AdvertisementDAO advertisementDAO;

    public ImageController(AdvertisementDAO advertisementDAO) {
        this.advertisementDAO = advertisementDAO;
    }


//    @RequestMapping(value = "/sid", method = RequestMethod.GET,
//            produces = MediaType.IMAGE_JPEG_VALUE)
//
//    public void getImage(HttpServletResponse response) throws IOException {
//        var imgFile = new ClassPathResource("image/sid.jpg");
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
//    }

    @RequestMapping(value = "/image", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@RequestParam int id){

        byte[] image = advertisementDAO.getImageByAdvertisementId(id);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }
}

