package com.example.comics.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.comics.domain.models.Comic
import com.example.comics.domain.usecase.GetComicsUseCase
import com.example.comics.presentation.viewmodel.HomeViewModel
import com.example.comics.util.Result
import com.example.comics.util.Result.Success
import com.example.comics.util.UiState
import com.example.comics.utils.CoroutinesTestRule
import com.example.comics.utils.fakeComicList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = CoroutinesTestRule()

    private val getComicsUseCase = mockk<GetComicsUseCase>(relaxed = true)
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        homeViewModel = HomeViewModel(getComicsUseCase)
    }

    @Test
    fun `getComics should update state from Loading to Success on request with success response`() =
        runTest {
            // GIVEN
            val fakeResult = Success(fakeComicList)
            coEvery { getComicsUseCase() } returns fakeResult

            val emittedStates = mutableListOf<UiState<List<Comic>>>()
            val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
                homeViewModel.uiState.collect {
                    emittedStates.add(it)
                }
            }
            // WHEN
            homeViewModel.getComics()
            advanceUntilIdle()

            // THEN
            assertEquals(UiState.Loading<List<Comic>>()::class.java, emittedStates[1].javaClass)
            assertEquals(UiState.Success(fakeComicList)::class.java, emittedStates[2].javaClass)
            collectJob.cancel()
        }

    @Test
    fun `getComics should update state from Loading to Error on request with success Failure response`() =
        runTest {
            // GIVEN
            val fakeResult = Result.Failure(mockk())
            coEvery { getComicsUseCase() } returns fakeResult

            val emittedStates = mutableListOf<UiState<List<Comic>>>()
            val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
                homeViewModel.uiState.collect {
                    emittedStates.add(it)
                }
            }
            // WHEN
            homeViewModel.getComics()
            advanceUntilIdle()

            // THEN
            assertEquals(UiState.Loading<List<Comic>>()::class.java, emittedStates[1].javaClass)
            assertEquals(UiState.Error<List<Comic>>()::class.java, emittedStates[2].javaClass)
            collectJob.cancel()
        }
}