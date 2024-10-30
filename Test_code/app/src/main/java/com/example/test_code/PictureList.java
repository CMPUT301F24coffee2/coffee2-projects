package com.example.test_code;

import java.util.ArrayList;
import java.util.List;

public class PictureList {
    private List<Picture> pictures;

    public PictureList() {
        pictures = new ArrayList<>();
    }

    public void add(Picture picture) {
        pictures.add(picture);
    }

    public void remove(Picture picture) {
        pictures.remove(picture);
    }
}