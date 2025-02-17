package com.example.comics.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comics.databinding.FragmentHomeBinding
import com.example.comics.domain.models.Comic
import com.example.comics.presentation.adapter.Adapter
import com.example.comics.presentation.viewmodel.HomeViewModel
import com.example.comics.util.UiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModel<HomeViewModel>()

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
        setupSwipeList()
        setupCollect()
    }

    private fun setupCollect() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { uiState ->
                when (uiState) {
                    is UiState.Error -> setupErrorState()
                    is UiState.Loading -> setupLoadingState()
                    is UiState.Success -> setupComicsList(uiState.data)
                }
            }
        }
    }

    private fun setupSwipeList() = with(binding.swipeRefresh) {
        setOnRefreshListener {
            viewModel.getComics()
        }
    }

    private fun setupComicsList(list: List<Comic>) {
        with(binding) {
            errorTV.visibility = View.GONE
            listItem.visibility = View.VISIBLE
            listItem.adapter = Adapter(list)
            listItem.layoutManager = LinearLayoutManager(context)
            swipeRefresh.isRefreshing = false
        }
    }

    private fun setupErrorState() {
        with(binding) {
            listItem.visibility = View.GONE
            errorTV.visibility = View.VISIBLE
            swipeRefresh.isRefreshing = false
        }
    }

    private fun setupLoadingState() {
        binding.swipeRefresh.isRefreshing = true
    }
}