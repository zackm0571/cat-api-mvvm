package com.zackmathews.catpictures;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel that bridges data from {@link CatRepo}.
 * LiveData is used for properties.
 */
public class MainViewModel extends ViewModel {
    private CatRepo catRepo = new CatRepo();

    private MutableLiveData<List<CatImage>> catImages = catRepo.getCatImages(Constants.DEFAULT_IMAGES_TO_LOAD);
    private MutableLiveData<List<CatBreed>> breeds = catRepo.getBreeds();
    private MutableLiveData<List<CatCategory>> categories = catRepo.getCategories();

    private MutableLiveData<List<CatCategory>> categoriesSelected = new MutableLiveData<>();
    private MutableLiveData<List<CatBreed>> breedsSelected = catRepo.getBreeds();


    public MainViewModel(){
    }

    /**
     * Retrieves n {@link CatImage}s filtered by Breed or Category.
     * Currently thecatapi returns an empty array when trying to search
     * with more than one category OR breed. The number of results is
     * currently defined by {@link Constants#DEFAULT_IMAGES_TO_LOAD} which is 10.
     * Note: given more time I would split up this api call.
     *
     * @param catBreeds     list of {@link CatBreed}
     * @param catCategories list of {@link CatCategory}
     * @return list of n {@link CatImage}s
     */
    public MutableLiveData<List<CatImage>> getImagesFromSearchFilter(List<CatBreed> catBreeds, List<CatCategory>catCategories){
        return catRepo.getCatImagesFromSearchFilters(Constants.DEFAULT_IMAGES_TO_LOAD, catBreeds, catCategories);
    }

    /**
     * Returns the catImages LiveData object. Does not perform a network call.
     * @return live data list of cat images.
     */
    public MutableLiveData<List<CatImage>> getCatImages() {
        return catImages;
    }
    /**
     * Returns the categories LiveData object. Does not perform a network call.
     * Note: given more time I would add network / room storage fallback in
     * the event where network isn't available.
     * @return live data list of categories.
     */
    public MutableLiveData<List<CatCategory>> getCategories(){
        return categories;
    }
    /**
     * Returns the breeds LiveData object. Does not perform a network call.
     * Note: given more time I would add network / room storage fallback in
     * the event where network isn't available.
     * @return live data list of categories.
     */
    public MutableLiveData<List<CatBreed>> getBreeds(){
        return breeds;
    }

    //For future use.
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
