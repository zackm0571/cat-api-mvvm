package com.zackmathews.catpictures;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    public MutableLiveData<List<CatImage>> getCatImages(int n) {
        List<CatImage> results = new ArrayList<>();
        Response<List<CatImage>> response = null;
        Call<List<CatImage>> call = catApi.getRandomImage(n);
        Log.d(getClass().getSimpleName(), String.format("getCatImaqes(%d), request: %s", n, call.request().toString()));
        call.enqueue(new Callback<List<CatImage>>() {
            @Override
            public void onResponse(Call<List<CatImage>> call, Response<List<CatImage>> response) {
                if (response.body() != null) {
                    results.addAll(response.body());
                }
                catImages.setValue(results);
            }

            @Override
            public void onFailure(Call<List<CatImage>> call, Throwable t) {
                Log.d(getClass().getSimpleName(), String.format("getCatImages(%d) failed. Info: %s", n, call.request().toString()));
            }
        });
        return catImages;
    }


    public MutableLiveData<List<CatImage>> getCatImagesFromSearchFilters(List<CatBreed> catBreeds, List<CatCategory> catCategories) {
        AsyncTask.execute(() -> {
            List<CatImage> results = new ArrayList<>();
            Response<List<CatImage>> response = null;
            StringBuilder breedIds = new StringBuilder();
            StringBuilder categoryIds = new StringBuilder();
            for (int i = 0; i < catBreeds.size(); i++) {
                breedIds.append(catBreeds.get(i).getId());
                if (i < catBreeds.size() - 1) {
                    breedIds.append(",");
                }
            }
            for (int j = 0; j < catCategories.size(); j++) {
                categoryIds.append(catCategories.get(j).getId());
                if (j < catCategories.size() - 1) {
                    categoryIds.append(",");
                }
            }
            try {
                response = catApi.search(categoryIds.toString(), breedIds.toString()).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response.body() != null) {
                results.addAll(response.body());
            }
            catImages.postValue(results);
        });
        return catImages;
    }

    public MutableLiveData<List<CatBreed>> getBreeds() {
        List<CatBreed> results = new ArrayList<>();
        catApi.getBreeds().enqueue(new Callback<List<CatBreed>>() {
            @Override
            public void onResponse(Call<List<CatBreed>> call, Response<List<CatBreed>> response) {
                if (response.body() != null) {
                    results.addAll(response.body());
                }
                breeds.postValue(results);
            }

            @Override
            public void onFailure(Call<List<CatBreed>> call, Throwable t) {
                Log.d(getClass().getSimpleName(), String.format("getBreeds() failed: %s", call.request().toString()));
            }
        });
        return breeds;
    }

    public MutableLiveData<List<CatCategory>> getCategories() {
            List<CatCategory> results = new ArrayList<>();
            catApi.getCategories().enqueue(new Callback<List<CatCategory>>() {
                @Override
                public void onResponse(Call<List<CatCategory>> call, Response<List<CatCategory>> response) {
                    if (response.body() != null) {
                        results.addAll(response.body());
                    }
                    categories.postValue(results);
                }

                @Override
                public void onFailure(Call<List<CatCategory>> call, Throwable t) {
                    Log.d(getClass().getSimpleName(), String.format("getCategories() failed: %s", call.request().toString()));
                }
            });
        return categories;
    }
}
