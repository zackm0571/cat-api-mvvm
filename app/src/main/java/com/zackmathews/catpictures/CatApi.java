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
    Call<List<CatImage>> searchByBreed(@Query("breed_ids") String breedId);

    @GET("/v1/images/search")
    Call<List<CatImage>> searchByCategory(@Query("category_ids") String categoryId);

    @GET("/v1/images/search")
    Call<List<CatImage>> search(@Query("category_ids") String categoryIds, @Query("breed_ids") String breedIds);

    @GET("/v1/images/search")
    Call<List<CatImage>> getRandomImage(@Query("limit") int n);
}
