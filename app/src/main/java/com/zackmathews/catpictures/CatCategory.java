package com.zackmathews.catpictures;

import androidx.annotation.NonNull;

public class CatCategory {
    int id;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @NonNull
    @Override
    public String toString() {
        return String.format("id: %s, name:%s", id, name);
    }
}
