package com.example.comics.util

import com.example.comics.data.network.client.MarvelClient
import com.example.comics.data.network.service.ComicsRemoteProvider
import com.example.comics.data.network.service.ComicsRemoteProviderImpl
import com.example.comics.data.repository.ComicsRepositoryImpl
import com.example.comics.domain.repository.ComicsRepository
import com.example.comics.domain.usecase.GetComicsUseCase
import com.example.comics.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val homeModule = module {
    factory<ComicsRepository> { ComicsRepositoryImpl(get()) }
    factory<GetComicsUseCase> { GetComicsUseCase(get()) }
    viewModel { HomeViewModel(get()) }
}

val networkModules = module {
    factory<ComicsRemoteProvider> { ComicsRemoteProviderImpl(get()) }
    factory<MarvelClient> {
        Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/v1/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarvelClient::class.java)
    }
}

