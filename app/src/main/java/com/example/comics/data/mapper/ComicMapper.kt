package com.example.comics.data.mapper

import com.example.comics.data.dto.ItemModel
import com.example.comics.data.dto.ResultModel
import com.example.comics.domain.models.Comic

fun ResultModel.toComicModel() =
    Comic(
        image = "${thumbnail.path}.${thumbnail.extension}",
        title = title,
        subtitle = description ?: "Sem descricao"
    )

fun List<ResultModel>.toComicModelList() = map { it.toComicModel() }