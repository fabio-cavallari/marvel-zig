package com.example.comics.utils

import com.example.comics.data.dto.DataModel
import com.example.comics.data.dto.ItemModel
import com.example.comics.data.dto.ResultModel
import com.example.comics.data.dto.ThumbnailModel
import com.example.comics.data.mapper.toComicModelList

val fakeItemModel = ItemModel(
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

val fakeComicList = fakeItemModel.data.results.toComicModelList()