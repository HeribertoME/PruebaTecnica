package com.example.pruebatecnica.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.pruebatecnica.databinding.FragmentDetailBinding
import com.example.pruebatecnica.presentation.models.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: PokemonDetailFragmentArgs by navArgs()
    private val viewModel: PokemonDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemonId = args.pokemonId
        viewModel.getPokemonById(pokemonId)

        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {
                    is UiState.Loading -> {
                        // Show loading state
                    }
                    is UiState.Success -> {
                        // Update UI with the pokemon details
                        val pokemon = state.data
                        binding.apply {
                            pokemonImageView.setImageUrl(pokemon.imageUrl)
                            pokemonImageView.setText(pokemon.name)
                            pokemonNameTextView.text = pokemon.name
                            pokemonHeightTextView.text = "Height: ${pokemon.height}"
                            pokemonWeightTextView.text = "Weight: ${pokemon.weight}"
                            pokemonTypesTextView.text = "Types: ${pokemon.types.joinToString(", ")}"
                        }
                    }
                    is UiState.Error -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}