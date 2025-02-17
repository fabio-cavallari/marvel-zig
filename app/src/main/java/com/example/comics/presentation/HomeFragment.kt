package com.example.comics.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comics.databinding.FragmentHomeBinding
import com.example.comics.domain.models.Comic
import com.example.comics.util.UiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment: Fragment(), IView {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        FragmentHomeBinding.inflate(layoutInflater, container, false).also {
            binding = it
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeList()
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { uiState ->
                when (uiState) {
                    is UiState.Error -> error()
                    is UiState.Loading -> binding.swipeRefresh.isRefreshing = true
                    is UiState.Success -> viewList(uiState.data)
                }
            }
        }

        viewModel.getComics()
    }
    private fun swipeList() = with(binding.swipeRefresh) {
        this.setOnRefreshListener {
            viewModel.getComics()
        }
    }

    override fun viewList(list: List<Comic>) {
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