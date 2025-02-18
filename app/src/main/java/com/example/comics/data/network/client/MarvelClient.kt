package com.example.comics.data.network.client

import com.example.comics.data.dto.ComicResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelClient {

    @GET("comics")
    suspend fun getComics(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ) : Response<ComicResponseDto>
}