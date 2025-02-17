package com.example.comics.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comics.databinding.FragmentHomeBinding
import com.example.comics.interactor.Interactor
import com.example.comics.presenter.Presenter
import kotlinx.coroutines.launch

class HomeFragment: Fragment(), IView {

    private val interactor: Interactor = Interactor(Presenter(this))

    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FragmentHomeBinding.inflate(layoutInflater, container, false).also {
            binding = it
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeList()
        refrash()
    }
    private fun swipeList() = with(binding.swipeRefresh) {
        this.setOnRefreshListener {
            refrash()
        }
    }

    override fun refrash() {
        with(binding) {
            this.swipeRefresh.isRefreshing = true
            lifecycle.coroutineScope.launch {
                interactor.getComics()
            }
        }
    }

    override fun viewList(list: List<ItemVO>) {
        with(binding) {
            this.errorTV.visibility = View.GONE
            this.listItem.visibility = View.VISIBLE
            this.listItem.adapter = Adapter(list)
            this.listItem.layoutManager = LinearLayoutManager(context)
            this.swipeRefresh.isRefreshing = false
        }
    }

    override fun error() {
        with(binding) {
            this.listItem.visibility = View.GONE
            this.errorTV.visibility = View.VISIBLE
            this.swipeRefresh.isRefreshing = false
        }
    }
}