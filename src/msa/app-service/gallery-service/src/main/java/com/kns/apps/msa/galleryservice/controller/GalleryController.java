package com.kns.apps.msa.galleryservice.controller;

import com.kns.apps.msa.commonpack.controller.BaseController;
import com.kns.apps.msa.commonpack.core.model.ResponseDto;
import com.kns.apps.msa.galleryservice.entity.Gallery;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class GalleryController extends BaseController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

//    @GetMapping("/")
//    @HystrixCommand(defaultFallback = "defaultFallback")
//    public String home() {
//        // This is useful for debugging
//        // When having multiple instance of gallery service running at different ports.
//        // We load balance among them, and display which instance received the request.
//        return "Hello from Gallery Service running at port: " + env.getProperty("local.server.port");
//    }

    // -------- Admin Area --------
    // This method should only be accessed by users with role of 'admin'
    // We'll add the logic of role based auth later
//    @GetMapping("/admin")
//    public String homeAdmin() {
//        return "This is the admin area of Gallery service running at port: " + env.getProperty("local.server.port");
//    }

    @GetMapping("/{id}")
    @HystrixCommand(defaultFallback = "defaultFallback")
    public ResponseEntity<ResponseDto> getGallery(@PathVariable final int id) {
        log.info("Creating gallery object ... ");
        // create gallery object
        Gallery gallery = new Gallery();
        gallery.setId(id);

        // get list of available images
        @SuppressWarnings("unchecked")    // we'll throw an exception from image service to simulate a failure
        List<Object> images = restTemplate.getForObject("http://image-service/images/", List.class);
        gallery.setImages(images);

        log.info("Returning images ... ");
        ResponseDto res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, gallery);
        return new ResponseEntity(res, HttpStatus.OK);
    }

}

