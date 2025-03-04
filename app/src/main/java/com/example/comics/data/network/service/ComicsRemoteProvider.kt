package com.example.comics.data.network.service

import com.example.comics.data.dto.ComicResponseDto
import retrofit2.Response

interface ComicsRemoteProvider {
    suspend fun getComics(): Response<ComicResponseDto>
}