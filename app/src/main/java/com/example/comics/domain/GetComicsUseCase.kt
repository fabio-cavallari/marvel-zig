package com.example.comics.domain

import com.example.comics.data.repository.Repository
import com.example.comics.domain.models.Comic
import com.example.comics.util.Result

class GetComicsUseCase {
    suspend operator fun invoke(): Result<List<Comic>> {
        val repository = Repository()
        return repository.getComics()
    }
}