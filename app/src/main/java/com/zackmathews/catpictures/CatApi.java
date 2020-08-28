package com.zackmathews.catpictures;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit interface for api.thecatapi.com. Access through {@link CatRepo}.
 */
public interface CatApi {
    /**
     * Gets the complete list of breeds that can be individually used as search filters.
     *
     * @return A list of {@link CatBreed}s.
     */
    @GET("/v1/breeds")
    Call<List<CatBreed>> getBreeds();

    /**
     * Gets the complete list of catgeories that can be individually used as search filters.
     *
     * @return A list of {@link CatCategory}s.
     */
    @GET("/v1/categories")
    Call<List<CatCategory>> getCategories();

    /**
     * Query the cat api and filter by breed. v1 of thecatapi does not allow querying with multiple breeds, the api will return an empty array.
     *
     * @param n        the number of results to return
     * @param breedIds Comma separated list of id's. As of now the cat api only allows one id.
     * @return A list of {@link CatImage}s.
     */
    @GET("/v1/images/search")
    Call<List<CatImage>> searchByBreed(@Query("limit") int n, @Query(value = "breed_ids", encoded = true) String breedIds);

    /**
     * Query the cat api and filter by category. v1 of thecatapi does not allow querying with multiple categories, the api will return an empty array.
     *
     * @param n           the number of results to return
     * @param categoryIds Comma separated list of id's. As of now the cat api only allows one id.
     * @return A list of {@link CatImage}s.
     */
    @GET("/v1/images/search")
    Call<List<CatImage>> searchByCategory(@Query("limit") int n, @Query(value = "category_ids", encoded = true) String categoryIds);

    /**
     * The current version of the api does not support this. Query the cat api and filter by category and breed. v1 of thecatapi does not allow querying with multiple categories + breeds, the api will return an empty array.
     *
     * @param n           the number of results to return
     * @param categoryIds Comma separated list of id's. As of now the cat api only allows one id.
     * @return A list of {@link CatImage}s.
     */
    @Deprecated
    @GET("/v1/images/search")
    Call<List<CatImage>> search(@Query("limit") int n, @Query(value = "category_ids", encoded = true) String categoryIds, @Query(value = "breed_ids", encoded = true) String breedIds);

    /**
     * Returns random images.
     *
     * @param n the number of images to return
     * @return
     */
    @GET("/v1/images/search")
    Call<List<CatImage>> getRandomImage(@Query("limit") int n);
}
