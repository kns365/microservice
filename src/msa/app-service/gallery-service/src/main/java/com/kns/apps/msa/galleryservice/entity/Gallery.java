package com.kns.apps.msa.galleryservice.entity;

import java.util.ArrayList;
import java.util.List;

public class Gallery {
    private Integer id;
    private List<Object> images = new ArrayList<>();

    public Gallery(Integer id, List<Object> images) {
        this.id = id;
        this.images = images;
    }

    public Gallery(Integer id, Object images) {
        this.id = id;
        this.images.add(images);
    }

    public Gallery() {
    }

    public Gallery(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Object> getImages() {
        return images;
    }

    public void setImages(List<Object> images) {
        this.images = images;
    }
}
