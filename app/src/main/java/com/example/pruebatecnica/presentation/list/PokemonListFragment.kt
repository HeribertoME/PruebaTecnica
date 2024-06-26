package com.example.pruebatecnica.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnica.R
import com.example.pruebatecnica.databinding.FragmentListBinding
import com.example.pruebatecnica.presentation.adapters.PokemonAdapter
import com.example.pruebatecnica.presentation.models.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonListFragment : Fragment(R.layout.fragment_list) {

    private val viewModel: PokemonListViewModel by viewModels()
    private lateinit var adapter: PokemonAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = DataBindingUtil.bind<FragmentListBinding>(view)

        adapter = PokemonAdapter {
            /*findNavController().navigate(
                //PokemonListFragmentDirections.
            )*/
            Toast.makeText(requireContext(), "Next", Toast.LENGTH_SHORT).show()
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when(state) {
                    is UiState.Loading -> {
                        // progress VISIBLE
                    }
                    is UiState.Success -> {
                        // progress GONE
                        adapter.submitList(state.data)
                    }
                    is UiState.Error -> {
                        // progress GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

        binding?.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) {
                        viewModel.loadPokemonList()
                    }
                }
            })

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.refreshPokemonList()
                swipeRefreshLayout.isRefreshing = false
            }
        }

    }
}