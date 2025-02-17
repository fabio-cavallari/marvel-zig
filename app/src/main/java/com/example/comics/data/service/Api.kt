package com.example.comics.data.service

import com.example.comics.data.dto.ItemModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("comics")
    fun getComics(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ) : Call<ItemModel>
}