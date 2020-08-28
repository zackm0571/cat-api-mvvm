package com.zackmathews.catpictures;

import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Unit tests for thecatapi. Includes partial coverage of {@link CatApi} and {@link CatRepo}.
 * Ran out of time before I could get full test coverage.
 * Given more time I would get full test coverage as well as add parameterized tests
 * for the different breeds / categories.
 */
public class CatApiUnitTest {
    @Test
    public void canGetBreedsFromCatApi() throws InterruptedException {
        CountDownLatch requestLatch = new CountDownLatch(1);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_CAT_API_URL).addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient.Builder().addInterceptor(chain -> {
            Request request = chain.request();
            request.newBuilder().addHeader("x-api-key", BuildConfig.API_KEY).build();
            okhttp3.Response response = chain.proceed(request);
            return response;
        }).build()).build();
        CatApi catApi = retrofit.create(CatApi.class);
        Call<List<CatBreed>> catBreedCall = catApi.getBreeds();
        catBreedCall.enqueue(new Callback<List<CatBreed>>() {
            @Override
            public void onResponse(Call<List<CatBreed>> call, Response<List<CatBreed>> response) {
                if (response.body() == null) {
                    try {
                        System.out.println(call.request().toString());
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fail();
                }
                System.out.println("Breeds:");
                for (CatBreed breed : response.body()) {
                    System.out.println(breed.toString());
                }
                requestLatch.countDown();
            }

            @Override
            public void onFailure(Call<List<CatBreed>> call, Throwable t) {
                System.out.println(call.request().toString());
                fail();
            }
        });

        requestLatch.await(Constants.FIVE_SECOND_WAIT, TimeUnit.MILLISECONDS);
    }

    @Test
    public void canGetCategoriesFromCatApi() throws InterruptedException {
        CountDownLatch requestLatch = new CountDownLatch(1);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_CAT_API_URL).addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                request.newBuilder().addHeader("x-api-key", BuildConfig.API_KEY).build();
                okhttp3.Response response = chain.proceed(request);
                return response;
            }
        }).build()).build();
        CatApi catApi = retrofit.create(CatApi.class);
        Call<List<CatCategory>> categoryCall = catApi.getCategories();
        categoryCall.enqueue(new Callback<List<CatCategory>>() {
            @Override
            public void onResponse(Call<List<CatCategory>> call, Response<List<CatCategory>> response) {
                if (response.body() == null) {
                    try {
                        System.out.println(call.request().toString());
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fail();
                }
                System.out.println("Categories:");
                for (CatCategory category : response.body()) {
                    System.out.println(category.toString());
                }
                requestLatch.countDown();
            }

            @Override
            public void onFailure(Call<List<CatCategory>> call, Throwable t) {
                System.out.println(call.request().toString());
                fail();
            }
        });
        requestLatch.await(Constants.FIVE_SECOND_WAIT, TimeUnit.MILLISECONDS);
    }

    @Test
    public void canSearchByCategory() throws InterruptedException {
        CountDownLatch requestLatch = new CountDownLatch(1);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_CAT_API_URL).addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                request.newBuilder().addHeader("x-api-key", BuildConfig.API_KEY).build();
                okhttp3.Response response = chain.proceed(request);
                return response;
            }
        }).build()).build();
        CatApi catApi = retrofit.create(CatApi.class);
        catApi.searchByCategory(Constants.DEFAULT_IMAGES_TO_LOAD, "1").enqueue(new Callback<List<CatImage>>() {
            @Override
            public void onResponse(Call<List<CatImage>> call, Response<List<CatImage>> response) {
                if (response.body() == null) {
                    try {
                        System.out.println(call.request().toString());
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fail();
                }
                System.out.println("Categories:");
                for (CatImage image : response.body()) {
                    System.out.println(image.toString());
                }
                requestLatch.countDown();
            }

            @Override
            public void onFailure(Call<List<CatImage>> call, Throwable t) {
                System.out.println(call.request().toString());
                fail();
            }
        });
        requestLatch.await(Constants.FIVE_SECOND_WAIT, TimeUnit.MILLISECONDS);
    }

    @Test
    public void canSearchByBreed() throws InterruptedException {
        CountDownLatch requestLatch = new CountDownLatch(1);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_CAT_API_URL).addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                request.newBuilder().addHeader("x-api-key", BuildConfig.API_KEY).build();
                okhttp3.Response response = chain.proceed(request);
                return response;
            }
        }).build()).build();
        CatApi catApi = retrofit.create(CatApi.class);
        catApi.searchByBreed(Constants.DEFAULT_IMAGES_TO_LOAD, "sibe").enqueue(new Callback<List<CatImage>>() {
            @Override
            public void onResponse(Call<List<CatImage>> call, Response<List<CatImage>> response) {
                if (response.body() == null) {
                    try {
                        System.out.println(call.request().toString());
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fail();
                }
                System.out.println("Categories:");
                for (CatImage image : response.body()) {
                    System.out.println(image.toString());
                }
                requestLatch.countDown();
            }

            @Override
            public void onFailure(Call<List<CatImage>> call, Throwable t) {
                System.out.println(call.request().toString());
                fail();
            }
        });
        requestLatch.await(Constants.FIVE_SECOND_WAIT, TimeUnit.MILLISECONDS);
    }
}