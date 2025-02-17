package com.example.comics.repository

import com.example.comics.CoroutinesTestRule
import com.example.comics.data.dto.DataModel
import com.example.comics.data.dto.ItemModel
import com.example.comics.data.dto.ResultModel
import com.example.comics.data.dto.ThumbnailModel
import com.example.comics.data.mapper.toComicModelList
import com.example.comics.data.network.service.ComicsRemoteProvider
import com.example.comics.data.repository.ComicsRepositoryImpl
import com.example.comics.util.Result.Failure
import com.example.comics.util.Result.Success
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class ComicsRepositoryTest {
    @get:Rule
    var mainCoroutineRule = CoroutinesTestRule()


    private val remoteProvider: ComicsRemoteProvider = mockk(relaxed = true)
    private val repository = ComicsRepositoryImpl(remoteProvider)
    private val fakeItemModel = ItemModel(
        data = DataModel(
            results = listOf(
                ResultModel(
                    title = "title",
                    description = "description",
                    thumbnail = ThumbnailModel(
                        path = "path",
                        extension = "extension"
                    )
                ),
                ResultModel(
                    title = "title b",
                    description = "description b",
                    thumbnail = ThumbnailModel(
                        path = "path b",
                        extension = "extension b"
                    )
                ),
            )
        )
    )

    @Test
    fun `getComics should return Success when api call is successful`() = runBlocking {
        // GIVEN
        val fakeSuccessResponse = Response.success(fakeItemModel)
        coEvery { remoteProvider.getComics() } returns fakeSuccessResponse

        // WHEN
        val result = repository.getComics()

        // THEN
        val expectedResult = Success(fakeItemModel.data.results.toComicModelList())
        assertEquals(expectedResult::class.java, result.javaClass)
        assertEquals(
            expectedResult.data.first().title,
            fakeItemModel.data.results.first().title
        )
        coVerify(exactly = 1) { remoteProvider.getComics() }
    }

    @Test
    fun `getComics should return Failure when api response is unsuccessful`() = runBlocking {
        // GIVEN
        val fakeRetrofitResponse = Response.error<ItemModel>(500, mockk(relaxed = true))
        coEvery { remoteProvider.getComics() } returns fakeRetrofitResponse

        // WHEN
        val result = repository.getComics()

        // THEN
        assertEquals(Failure::class.java, result.javaClass)
        coVerify(exactly = 1) { remoteProvider.getComics() }
    }
}