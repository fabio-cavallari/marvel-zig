package com.example.comics.util

sealed class UiState<T> {
    data class Success<T>(val data: T): UiState<T>()
    open class Error<T> : UiState<T>()
    open class Loading<T> : UiState<T>()
}