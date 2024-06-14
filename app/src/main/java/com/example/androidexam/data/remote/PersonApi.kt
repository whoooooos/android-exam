package com.example.androidexam.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Ivan Esguerra on 6/11/2024.
 **/
interface PersonApi {
    @GET("/api")
    suspend fun getPersons(
        @Query("page") page: Int,
        @Query("results") results: Int = 10,
        @Query("seeds") seeds: String = "abc"
    ): PersonDto

    companion object {
        const val BASE_URL = "https://randomuser.me"
    }
}