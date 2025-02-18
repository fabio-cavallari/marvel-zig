package com.example.comics.usecase

import com.example.comics.data.mapper.toComicModelList
import com.example.comics.domain.repository.ComicsRepository
import com.example.comics.domain.usecase.GetComicsUseCase
import com.example.comics.util.Result
import com.example.comics.utils.CoroutinesTestRule
import com.example.comics.utils.fakeItemModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class GetComicsListUseCaseTest {
    @get:Rule
    var mainCoroutineRule = CoroutinesTestRule()

    private val repository: ComicsRepository = mockk(relaxed = true)
    private val getComicsUseCase = GetComicsUseCase(repository)

    @Test
    fun `GetComicsUseCase should return Success when api call is successful`() = runBlocking {
        // GIVEN
        val fakeSuccessResponse = Result.Success(fakeItemModel.data.results.toComicModelList())
        coEvery { repository.getComics() } returns fakeSuccessResponse

        // WHEN
        val result = getComicsUseCase()

        // THEN
        val expectedResult = Result.Success(fakeItemModel.data.results.toComicModelList())
        Assert.assertEquals(expectedResult::class.java, result.javaClass)
        Assert.assertEquals(
            expectedResult.data.first().title,
            fakeItemModel.data.results.first().title
        )
        coVerify(exactly = 1) { getComicsUseCase() }
    }

    @Test
    fun `GetComicsUseCase should return Failure when api call is unsuccessful`() = runBlocking {
        // GIVEN
        val fakeException = RuntimeException("fake exception")
        val fakeRetrofitFailureResponse = Result.Failure(fakeException)
        coEvery { repository.getComics() } returns fakeRetrofitFailureResponse

        // WHEN
        val result = getComicsUseCase()

        // THEN
        val expectedResult = Result.Failure(fakeException)
        Assert.assertEquals(expectedResult::class.java, result.javaClass)
        Assert.assertEquals(
            expectedResult.error.message,
            (result as Result.Failure).error.message
        )
        coVerify(exactly = 1) { getComicsUseCase() }
    }
}