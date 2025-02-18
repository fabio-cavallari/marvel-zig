package com.example.comics.data.mapper

import com.example.comics.data.dto.ComicDto
import com.example.comics.domain.models.Comic

fun ComicDto.toComicDomainModel() =
    Comic(
        image = "${thumbnail.path}.${thumbnail.extension}",
        title = title,
        subtitle = description ?: "Sem descricao"
    )

fun List<ComicDto>.toComicModelList() = map { it.toComicDomainModel() }