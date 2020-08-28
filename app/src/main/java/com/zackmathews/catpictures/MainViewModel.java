package com.zackmathews.catpictures;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private CatRepo catRepo = new CatRepo();

    private MutableLiveData<List<CatImage>> catImages = catRepo.getCatImages(Constants.DEFAULT_IMAGES_TO_LOAD);
    private MutableLiveData<List<CatBreed>> breeds = catRepo.getBreeds();
    private MutableLiveData<List<CatCategory>> categories = catRepo.getCategories();

    private MutableLiveData<List<CatCategory>> categoriesSelected = new MutableLiveData<>();
    private MutableLiveData<List<CatBreed>> breedsSelected = catRepo.getBreeds();


    public MainViewModel(){
    }


    public MutableLiveData<List<CatImage>> getImagesFromSearchFilter(List<CatBreed> catBreeds, List<CatCategory>catCategories){
        return catRepo.getCatImagesFromSearchFilters(Constants.DEFAULT_IMAGES_TO_LOAD, catBreeds, catCategories);
    }
    public MutableLiveData<List<CatImage>> getCatImages() {
        return catImages;
    }

    public MutableLiveData<List<CatCategory>> getCategories(){
        return categories;
    }
    public MutableLiveData<List<CatBreed>> getBreeds(){
        return breeds;
    }

    public MutableLiveData<List<CatCategory>> getCategoriesSelected() {
        return categoriesSelected;
    }

    public void setCategoriesSelected(List<CatCategory> selected) {
        categoriesSelected.setValue(selected);
    }

    public MutableLiveData<List<CatBreed>> getBreedsSelected() {
        return breedsSelected;
    }

    public void setBreedsSelected(List<CatBreed> selected) {
        breedsSelected.setValue(selected);
    }
}
