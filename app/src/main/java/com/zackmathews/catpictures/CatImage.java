package com.zackmathews.catpictures;

import java.util.List;

import androidx.annotation.NonNull;

public class CatImage {
    String id;
    String url;
    List<CatCategory> categories;
    List<CatBreed> breeds;

    @NonNull
    @Override
    public String toString() {
        return String.format("id: %s, url: %s, categories: %s, breeds: %s", id, url, (categories != null) ? categories.toString() : null, (breeds != null) ? breeds.toString() : null);
    }
}
