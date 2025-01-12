package com.example.pruebatecnica.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnica.R
import com.example.pruebatecnica.databinding.ItemPokemonBinding
import com.example.pruebatecnica.domain.model.Pokemon

class PokemonAdapter(
    private val onClickItem: (Pokemon) -> Unit,
    private val onFavoriteIconClick: (Pokemon) -> Unit
) : ListAdapter<Pokemon, PokemonAdapter.PokemonViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        if (pokemon != null){
            holder.bind(pokemon)
        }
    }

    inner class PokemonViewHolder(private val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            binding.tvPokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            binding.civPokemonImage.setImageUrl(pokemon.imageUrl)
            binding.civPokemonImage.setText(pokemon.name)
            binding.ivFavoriteIcon.setImageResource(
                if (pokemon.isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite
            )

            binding.linearLayoutItemPokemon.setOnClickListener { onClickItem(pokemon) }
            binding.ivFavoriteIcon.setOnClickListener { onFavoriteIconClick(pokemon) }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem == newItem
            }
        }
    }
}