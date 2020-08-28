package com.zackmathews.catpictures;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Model for thecatapi image result object. {@link CatRepo} includes api access.
 */
public class CatImage {
    String id;
    String url;
    List<CatCategory> categories;
    List<CatBreed> breeds;

    @NonNull
    @Override
    public String toString() {
        return String.format("id: %s\nurl: %s\ncategories: %s\nbreeds: %s", id, url, (categories != null) ? categories.toString() : null, (breeds != null) ? breeds.toString() : null);
    }
}
