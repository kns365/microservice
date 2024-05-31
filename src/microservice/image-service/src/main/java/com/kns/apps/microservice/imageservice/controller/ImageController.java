package com.kns.apps.microservice.imageservice.controller;

import com.kns.apps.microservice.imageservice.entity.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class ImageController {
    @Autowired
    private Environment env;

    @RequestMapping("/")
    public String home() {
        // This is useful for debugging
        // When having multiple instance of image service running at different ports.
        // We load balance among them, and display which instance received the request.
        return "Hello from Image Service running at port: " + env.getProperty("local.server.port");
    }

    @RequestMapping("/images")
    public List<Image> getImages() throws InterruptedException {
        System.out.println("getImages " + env.getProperty("local.server.port"));
        List<Image> images = Arrays.asList(
                new Image(0, "Hello from Image Service running at port: " + env.getProperty("local.server.port"), "http://localhost:" + env.getProperty("local.server.port") + "/images/"),
                new Image(1, "Treehouse of Horror V", "https://www.imdb.com/title/tt0096697/mediaviewer/rm3842005760"),
                new Image(2, "The Town", "https://www.imdb.com/title/tt0096697/mediaviewer/rm3698134272"),
                new Image(3, "The Last Traction Hero", "https://www.imdb.com/title/tt0096697/mediaviewer/rm1445594112"));
//        Thread.sleep(30000);
////        int a = 9 / 0;
//        try {
//            throw new Exception("Images can't be fetched");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        log.info("getImages");
        return images;
    }
}


