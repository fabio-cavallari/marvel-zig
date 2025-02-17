package com.example.comics.presenter

import com.example.comics.data.dto.ItemModel
import com.example.comics.presentation.IView
import com.example.comics.domain.models.Comic

class Presenter(private val iview: IView) : IPresenter {

    override fun setupList(list: ItemModel) {
        iview.viewList(
            list = list.data.results.map {
                Comic(
                    image = "${it.thumbnail.path}.${it.thumbnail.extension}",
                    title = it.title,
                    subtitle = it.description ?: "Sem descricao"
                )
            }
        )
    }

    override fun error() {
        iview.error()
    }
}