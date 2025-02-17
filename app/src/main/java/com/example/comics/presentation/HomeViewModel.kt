package com.example.comics.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comics.domain.GetComicsUseCase
import com.example.comics.domain.models.Comic
import com.example.comics.util.Result
import com.example.comics.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _uiState : MutableStateFlow<UiState<List<Comic>>> = MutableStateFlow(UiState.Loading())
    val uiState get() = _uiState

    fun getComics() {
        viewModelScope.launch {
            val comicsResult = GetComicsUseCase().invoke()
            when (comicsResult) {
                is Result.Success -> _uiState.value = UiState.Success(comicsResult.data)
                is Result.Failure -> _uiState.value = UiState.Error()
            }
        }
    }
}