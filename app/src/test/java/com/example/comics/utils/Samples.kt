package com.example.comics.utils

import com.example.comics.data.dto.ComicDataDto
import com.example.comics.data.dto.ComicDto
import com.example.comics.data.dto.ComicResponseDto
import com.example.comics.data.dto.ThumbnailDto
import com.example.comics.data.mapper.toComicModelList

val fakeComicResponse = ComicResponseDto(
    data = ComicDataDto(
        results = listOf(
            ComicDto(
                title = "title",
                description = "description",
                thumbnail = ThumbnailDto(
                    path = "path",
                    extension = "extension"
                )
            ),
            ComicDto(
                title = "title b",
                description = "description b",
                thumbnail = ThumbnailDto(
                    path = "path b",
                    extension = "extension b"
                )
            ),
        )
    )
)

val fakeComicList = fakeComicResponse.data.results.toComicModelList()