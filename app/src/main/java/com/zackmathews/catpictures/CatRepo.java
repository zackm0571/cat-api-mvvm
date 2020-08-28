package com.zackmathews.catpictures;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Repo for thecatapi. Set API key in ${project root}/catapi.properties.
 */
public class CatRepo {
    private MutableLiveData<List<CatImage>> catImages = new MutableLiveData<>();
    private MutableLiveData<List<CatCategory>> categories = new MutableLiveData<>();
    private MutableLiveData<List<CatBreed>> breeds = new MutableLiveData<>();

    private Retrofit retrofit = new Retrofit.Builder()
            .client(new OkHttpClient())
            .baseUrl(Constants.BASE_CAT_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private CatApi catApi = retrofit.create(CatApi.class);

    /**
     * Retrieves n results of random {@link CatImage}s.
     *
     * @param n number of results
     * @return List of {@link CatImage}s.
     */
    public MutableLiveData<List<CatImage>> getCatImages(int n) {
        List<CatImage> results = new ArrayList<>();
        Call<List<CatImage>> call = catApi.getRandomImage(n);
        call.enqueue(new Callback<List<CatImage>>() {
            @Override
            public void onResponse(Call<List<CatImage>> call, Response<List<CatImage>> response) {
                if (response.body() != null) {
                    results.addAll(response.body());
                    Log.d(getClass().getSimpleName(), String.format("getCatImaqes(%d), request: %s", n, call.request().toString()));
                    Log.d(getClass().getSimpleName(), String.format("Results: %s", results.toString()));
                }
                catImages.postValue(results);
            }

            @Override
            public void onFailure(Call<List<CatImage>> call, Throwable t) {
                t.printStackTrace();
                Log.d(getClass().getSimpleName(), String.format("getCatImages(%d) failed. Info: %s", n, call.request().toString()));
            }
        });
        return catImages;
    }

    /**
     * Retrieves n {@link CatImage}s filtered by Breed or Category.
     * Currently thecatapi returns an empty array when trying to search
     * with more than one category OR breed. Note: given more time I would split up
     * this api call.
     *
     * @param n             number of desired results
     * @param catBreeds     list of {@link CatBreed}
     * @param catCategories list of {@link CatCategory}
     * @return list of n {@link CatImage}s
     */
    public MutableLiveData<List<CatImage>> getCatImagesFromSearchFilters(int n, List<CatBreed> catBreeds, List<CatCategory> catCategories) {
        List<CatImage> results = new ArrayList<>();
        StringBuilder breedIds = new StringBuilder("");
        StringBuilder categoryIds = new StringBuilder("");
        for (int i = 0; i < catBreeds.size(); i++) {
            breedIds.append(catBreeds.get(i).getId());
            if (i != catBreeds.size() - 1) {
                breedIds.append(",");
            }
        }
        for (int j = 0; j < catCategories.size(); j++) {
            categoryIds.append(catCategories.get(j).getId());
            if (j != catCategories.size() - 1) {
                categoryIds.append(",");
            }
        }
        Call<List<CatImage>> call = (catBreeds.size() > 0 && catCategories.size() > 0) ?
                catApi.search(n, categoryIds.toString(), breedIds.toString()) :
                (catBreeds.size() > 0) ? catApi.searchByBreed(n, breedIds.toString()) :
                        catApi.searchByCategory(n, categoryIds.toString());
        Log.d(getClass().getSimpleName(), String.format("getCatImaqesFromSearchFilters(%d), request: %s", n, call.request().toString()));
        call.enqueue(new Callback<List<CatImage>>() {
            @Override
            public void onResponse(Call<List<CatImage>> call, Response<List<CatImage>> response) {
                if (response.body() != null) {
                    results.addAll(response.body());
                    Log.d(getClass().getSimpleName(), String.format("Results: %s", results.toString()));
                }
                catImages.postValue(results);
            }

            @Override
            public void onFailure(Call<List<CatImage>> call, Throwable t) {
                t.printStackTrace();
                Log.d(getClass().getSimpleName(), String.format("getCatImagesFromSearchFilters(%d) failed. Info: %s", n, call.request().toString()));
            }
        });
        return catImages;
    }

    /**
     * Retrieves all breeds supported by thecatapi platform.
     *
     * @return list of {@link CatBreed}s
     */
    public MutableLiveData<List<CatBreed>> getBreeds() {
        List<CatBreed> results = new ArrayList<>();
        Call<List<CatBreed>> call = catApi.getBreeds();
        Log.d(getClass().getSimpleName(), String.format("getBreeds(), request: %s", call.request().toString()));
        call.enqueue(new Callback<List<CatBreed>>() {
            @Override
            public void onResponse(Call<List<CatBreed>> call, Response<List<CatBreed>> response) {
                if (response.body() != null) {
                    results.addAll(response.body());
                    Log.d(getClass().getSimpleName(), String.format("Results: %s", results.toString()));
                }
                breeds.postValue(results);
            }

            @Override
            public void onFailure(Call<List<CatBreed>> call, Throwable t) {
                t.printStackTrace();
                Log.d(getClass().getSimpleName(), String.format("getBreeds() failed, request: %s", call.request().toString()));
            }
        });
        return breeds;
    }

    /**
     * Retrieves all categories supported by thecatapi platform.
     *
     * @return list of {@link CatCategory}s
     */
    public MutableLiveData<List<CatCategory>> getCategories() {
        List<CatCategory> results = new ArrayList<>();
        Call<List<CatCategory>> call = catApi.getCategories();
        Log.d(getClass().getSimpleName(), String.format("getCategories(), request: %s", call.request().toString()));

        call.enqueue(new Callback<List<CatCategory>>() {
            @Override
            public void onResponse(Call<List<CatCategory>> call, Response<List<CatCategory>> response) {
                if (response.body() != null) {
                    results.addAll(response.body());
                    Log.d(getClass().getSimpleName(), String.format("Results: %s", results.toString()));
                }
                categories.postValue(results);
            }

            @Override
            public void onFailure(Call<List<CatCategory>> call, Throwable t) {
                t.printStackTrace();
                Log.d(getClass().getSimpleName(), String.format("getCategories() failed: %s", call.request().toString()));
            }
        });
        return categories;
    }
}
