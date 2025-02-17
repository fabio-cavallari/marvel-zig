package com.example.comics.presentation

import com.example.comics.domain.models.Comic

interface IView {

    fun viewList(list: List<Comic>)

    fun error()

}