package com.kns.apps.microservice.galleryservice.controller;

import com.kns.apps.microservice.configserver.core.model.ResponseDto;
import com.kns.apps.microservice.galleryservice.entity.Gallery;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class GalleryController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    private static final Logger LOGGER = LoggerFactory.getLogger(GalleryController.class);

    @HystrixCommand(fallbackMethod = "fallbackHome")
    @RequestMapping("/")
    public String home() {
        // This is useful for debugging
        // When having multiple instance of gallery service running at different ports.
        // We load balance among them, and display which instance received the request.
        return "Hello from Gallery Service running at port: " + env.getProperty("local.server.port");
    }

    public String fallbackHome() {
        return "The service is unavailable";
    }

    /*@HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping("/{id}")
    public Gallery getGallery(@PathVariable final int id) {
        LOGGER.info("Creating gallery object ... ");
        // create gallery object
        Gallery gallery = new Gallery();
        gallery.setId(id);

        // get list of available images
//        @SuppressWarnings("unchecked")    // we'll throw an exception from image service to simulate a failure
        List<Object> images = restTemplate.getForObject("http://image-service/images/", List.class);
        gallery.setImages(images);

        LOGGER.info("Returning images ... ");
        return gallery;
    }

    // a fallback method to be called if failure happened
    public Gallery fallback(int galleryId, Throwable hystrixCommand) {
        LOGGER.info("fallback ... ");
        List<Object> list = new ArrayList<>();
        Gallery temp = new Gallery(503, "The service is unavailable");
        list.add(temp);
        return new Gallery(503, list);
    }*/



    // -------- Admin Area --------
    // This method should only be accessed by users with role of 'admin'
    // We'll add the logic of role based auth later
    @RequestMapping("/admin")
    public String homeAdmin() {
        return "This is the admin area of Gallery service running at port: " + env.getProperty("local.server.port");
    }






    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping("/{id}")
    public ResponseEntity<?> getGallery(@PathVariable final int id) {
//        LOGGER.info("Creating gallery object ... ");
        // create gallery object
        Gallery gallery = new Gallery();
        gallery.setId(id);

        // get list of available images
        @SuppressWarnings("unchecked")    // we'll throw an exception from image service to simulate a failure
        List<Object> images = restTemplate.getForObject("http://image-service/images/", List.class);
        gallery.setImages(images);

//        LOGGER.info("Returning images ... ");
        log.info("Returning images ... ");
        ResponseDto res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, gallery);
        return new ResponseEntity(res, HttpStatus.OK);
    }
    public ResponseEntity<?> fallback(int galleryId, Throwable hystrixCommand) {
        log.info("fallback ... ");
        List<Object> list = new ArrayList<>();
        Gallery temp = new Gallery(503, "The service is unavailable");
        list.add(temp);
        ResponseDto res = new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name(), null, new Gallery(503, list));
        return new ResponseEntity(res, HttpStatus.OK);
    }
}

