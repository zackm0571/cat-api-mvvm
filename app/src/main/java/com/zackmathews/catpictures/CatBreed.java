package com.zackmathews.catpictures;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Model for thecatapi breed object.
 * Use with {@link CatRepo#getCatImagesFromSearchFilters(int, List, List)}
 * to filter results by breed.
 */
public class CatBreed {
    private String id;
    private String name;
    @SerializedName("life_span")
    private String lifespan;
    private String origin;
    @SerializedName("wikipedia_url")
    private String wikipediaUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLifespan() {
        return lifespan;
    }

    public void setLifespan(String lifespan) {
        this.lifespan = lifespan;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public void setWikipediaUrl(String wikipediaUrl) {
        this.wikipediaUrl = wikipediaUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("id: %s, name: %s, " +
                        "lifespan: %s, origin: %s, wikipediaUrl: %s",
                id, name, lifespan, origin, wikipediaUrl);
    }
}
