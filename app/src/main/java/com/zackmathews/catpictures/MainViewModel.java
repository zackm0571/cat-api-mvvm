package com.zackmathews.catpictures;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private CatRepo catRepo = new CatRepo();
    private MutableLiveData<List<CatImage>> catImages = catRepo.getCatImages(Constants.DEFAULT_IMAGES_TO_LOAD);

    public MainViewModel(){
    }


    public MutableLiveData<List<CatImage>> getCatImages() {
        return catImages;
    }
}
