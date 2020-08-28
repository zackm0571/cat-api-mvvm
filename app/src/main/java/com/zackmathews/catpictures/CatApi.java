package com.zackmathews.catpictures;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CatApi {
    @GET("/v1/breeds")
    Call<List<CatBreed>> getBreeds();

    @GET("/v1/categories")
    Call<List<CatCategory>> getCategories();

    @GET("/v1/images/search")
    Call<List<CatImage>> searchByBreed(@Query("limit") int n, @Query(value = "breed_ids", encoded = true) String breedIds);

    @GET("/v1/images/search")
    Call<List<CatImage>> searchByCategory(@Query("limit") int n, @Query(value = "category_ids", encoded = true) String categoryIds);

    @GET("/v1/images/search")
    Call<List<CatImage>> search(@Query("limit") int n, @Query(value = "category_ids", encoded = true) String categoryIds, @Query(value = "breed_ids", encoded = true) String breedIds);

    @GET("/v1/images/search")
    Call<List<CatImage>> getRandomImage(@Query("limit") int n);
}
