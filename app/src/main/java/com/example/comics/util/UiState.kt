package com.example.comics.util

sealed class UiState<T> {
    data class Success<T>(val data: T): UiState<T>()
    class Error<T> : UiState<T>()
    class Loading<T> : UiState<T>()
}