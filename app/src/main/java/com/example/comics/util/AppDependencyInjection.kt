package com.example.comics.util

import com.example.comics.data.repository.ComicsRepositoryImpl
import com.example.comics.domain.repository.ComicsRepository
import com.example.comics.domain.usecase.GetComicsUseCase
import com.example.comics.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module

val appModule = module {
    factory<ComicsRepository> { ComicsRepositoryImpl() }
    factory<GetComicsUseCase> { GetComicsUseCase(get()) }
    viewModel<HomeViewModel> { HomeViewModel(get()) }
}