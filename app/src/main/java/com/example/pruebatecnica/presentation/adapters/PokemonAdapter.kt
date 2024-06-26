package com.example.pruebatecnica.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnica.databinding.ItemPokemonBinding
import com.example.pruebatecnica.domain.model.Pokemon

class PokemonAdapter(
    private val onClickItem: (Pokemon) -> Unit
) : ListAdapter<Pokemon, PokemonAdapter.PokemonViewHolder>(PokemonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding, onClickItem)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PokemonViewHolder(private val binding: ItemPokemonBinding, val onClick: (Pokemon) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        private var currentPokemon: Pokemon? = null

        init {
            binding.root.setOnClickListener {
                currentPokemon?.let {
                    onClick(it)
                }
            }
        }

        fun bind(pokemon: Pokemon) {
            binding.tvPokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            binding.civPokemonImage.setImageUrl(pokemon.imageUrl)
            binding.civPokemonImage.setText(pokemon.name)
        }
    }

    class PokemonDiffCallback : DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }
    }
}