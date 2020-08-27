package com.zackmathews.catpictures;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CatRepo {
    private MutableLiveData<List<CatImage>> catImages = new MutableLiveData<>();
    private Retrofit retrofit = new Retrofit.Builder()
            .client(new OkHttpClient())
            .baseUrl(Constants.BASE_CAT_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private CatApi catApi = retrofit.create(CatApi.class);

    public MutableLiveData<List<CatImage>> getCatImages(int n) {
        AsyncTask.execute(() -> {
            List<CatImage> results = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                Response<List<CatImage>> response = null;
                try {
                    response = catApi.searchByCategory("1").execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (response.body() != null) {
                    results.addAll(response.body());
                }
            }
            catImages.postValue(results);
        });
        return catImages;
    }
}
