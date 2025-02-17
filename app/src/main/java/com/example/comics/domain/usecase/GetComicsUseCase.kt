package com.example.comics.domain.usecase

import com.example.comics.domain.models.Comic
import com.example.comics.domain.repository.ComicsRepository
import com.example.comics.util.Result

class GetComicsUseCase(private val repository: ComicsRepository) {
    suspend operator fun invoke(): Result<List<Comic>> {
        return repository.getComics()
    }
}