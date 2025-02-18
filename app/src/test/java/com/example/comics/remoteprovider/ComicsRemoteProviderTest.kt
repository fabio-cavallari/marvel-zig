package com.example.comics.remoteprovider

import com.example.comics.data.dto.ComicResponseDto
import com.example.comics.data.network.client.MarvelClient
import com.example.comics.data.network.service.ComicsRemoteProviderImpl
import com.example.comics.utils.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class ComicsRemoteProviderTest {
    @get:Rule
    var mainCoroutineRule = CoroutinesTestRule()


    private val mockClient = mockk<MarvelClient>(relaxed = true)
    private val remoteProvider = ComicsRemoteProviderImpl(mockClient)

    @Test
    fun `getComics should return response and no exception when client call is successful`() = runBlocking {
        // GIVEN
        val fakeSuccessRetrofitResponse: Response<ComicResponseDto> = mockk()
        coEvery { mockClient.getComics(any(), any(), any()) } returns fakeSuccessRetrofitResponse

        // WHEN
        val response = remoteProvider.getComics()

        // THEN
        assertEquals(fakeSuccessRetrofitResponse, response)
        coVerify(exactly = 1) { mockClient.getComics(any(), any(), any()) }
    }
    @Test
    fun `getComics should return exception and no ComicResponseDto when client call fails`() = runBlocking {
        // GIVEN
        val fakeException = RuntimeException("Unexpected error")
        coEvery { mockClient.getComics(any(), any(), any()) } throws fakeException

        // WHEN
        val response = try {
            remoteProvider.getComics()
        } catch (exception: Exception){
            exception
        }

        // THEN
        assertEquals(fakeException, response)
        coVerify(exactly = 1) { mockClient.getComics(any(), any(), any()) }
    }
}
