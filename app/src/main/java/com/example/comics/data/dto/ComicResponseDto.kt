package com.example.comics.data.dto

data class ComicResponseDto(
    val data: ComicDataDto
)

data class ComicDataDto(
    val results: List<ComicDto>
)

data class ComicDto(
    val title: String,
    val description: String?,
    val thumbnail: ThumbnailDto
)

data class ThumbnailDto(
    val path: String,
    val extension: String,
)