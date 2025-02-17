package com.example.comics.domain.repository

import com.example.comics.domain.models.Comic
import com.example.comics.util.Result

interface ComicsRepository {
    suspend fun getComics(): Result<List<Comic>>
}