package com.example.comics.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comics.domain.models.Comic
import com.example.comics.domain.usecase.GetComicsUseCase
import com.example.comics.util.Result
import com.example.comics.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getComicsUseCase: GetComicsUseCase
): ViewModel() {
    private val _uiState : MutableStateFlow<UiState<List<Comic>>> = MutableStateFlow(UiState.Loading())
    val uiState get() = _uiState

    init {
        getComics()
    }

    fun getComics() {
        _uiState.value = UiState.Loading()
        viewModelScope.launch {
            val comicsResult = getComicsUseCase()
            when (comicsResult) {
                is Result.Success -> _uiState.value = UiState.Success(comicsResult.data)
                is Result.Failure -> _uiState.value = UiState.Error()
            }
        }
    }
}