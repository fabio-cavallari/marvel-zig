package com.example.comics.data.network.service

import com.example.comics.data.dto.ComicResponseDto
import com.example.comics.data.network.client.MarvelClient
import retrofit2.Response

class ComicsRemoteProviderImpl(
    private val client: MarvelClient
) : ComicsRemoteProvider {
    override suspend fun getComics(): Response<ComicResponseDto> {
        return client.getComics(
            apikey = "b7e14bab409c70a5c4e7c2b319c09d7b",
            ts = "1682982412",
            hash = "3482f01e9bf207a437a4b621c91339ad",
        )
    }

}