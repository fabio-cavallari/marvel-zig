package com.example.comics.presentation

interface IView {

    fun viewList(list: List<ItemVO>)

    fun refrash()

    fun error()

}