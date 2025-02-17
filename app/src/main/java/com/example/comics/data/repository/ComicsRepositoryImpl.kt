package com.example.comics.data.repository

import com.example.comics.data.mapper.toComicModelList
import com.example.comics.data.network.service.ComicsRemoteProvider
import com.example.comics.domain.models.Comic
import com.example.comics.domain.repository.ComicsRepository
import com.example.comics.util.Result
import com.example.comics.util.safeRunDispatcher

class ComicsRepositoryImpl(
    private val remoteProvider: ComicsRemoteProvider
): ComicsRepository {
    override suspend fun getComics(): Result<List<Comic>> {
        val result = safeRunDispatcher {
            remoteProvider.getComics().body()!!.data.results
        }

        return when (result) {
            is Result.Success -> {
                val resultModelList = result.data
                Result.Success(resultModelList.toComicModelList())
            }
            is Result.Failure -> Result.Failure(result.error)
        }
    }

}