package com.example.comics.presenter

import com.example.comics.data.dto.ItemModel

interface IPresenter {

    fun setupList(list: ItemModel)

    fun error()
}