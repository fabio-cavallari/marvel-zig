package com.example.comics.data.repository

import com.example.comics.data.dto.ItemModel
import com.example.comics.data.mapper.toComicModelList
import com.example.comics.data.service.Api
import com.example.comics.domain.models.Comic
import com.example.comics.util.Result
import com.example.comics.util.safeRunDispatcher
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

class Repository {
    suspend fun getComics(): Result<List<Comic>> {
        val result = safeRunDispatcher {
            Retrofit.Builder()
                .baseUrl("https://gateway.marvel.com/v1/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Api::class.java).getComics(
                    apikey = "b7e14bab409c70a5c4e7c2b319c09d7b",
                    ts = "1682982412",
                    hash = "3482f01e9bf207a437a4b621c91339ad"
                ).await()
        }

        return when (result) {
            is Result.Success -> {
                val resultModelList = result.data.data.results
                Result.Success(resultModelList.toComicModelList())
            }
            is Result.Failure -> Result.Failure(result.error)
        }
    }

}