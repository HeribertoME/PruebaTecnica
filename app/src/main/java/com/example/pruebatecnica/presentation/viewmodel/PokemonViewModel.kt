package com.example.pruebatecnica.presentation.viewmodel

/*@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    private val insertPokemonUseCase: InsertPokemonUseCase,
    private val updatePokemonUseCase: UpdatePokemonUseCase,
    private val deletePokemonUseCase: DeletePokemonUseCase,
    private val getAllPokemonUseCase: GetAllPokemonUseCase
) : ViewModel() {

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList

    fun fetchPokemonList(limit: Int, offset: Int) {
        viewModelScope.launch {
            val pokemonList = getPokemonListUseCase(limit, offset)
            _pokemonList.value = pokemonList
        }
    }

    fun getPokemonDetail(id: Int): LiveData<Pokemon> {
        val pokemonDetail = MutableLiveData<Pokemon>()
        viewModelScope.launch {
            val detail = getPokemonDetailUseCase(id)
            pokemonDetail.value = detail
        }
        return pokemonDetail
    }

    fun addPokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            insertPokemonUseCase(pokemon)
        }
    }

    fun updatePokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            updatePokemonUseCase(pokemon)
        }
    }

    fun deletePokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            deletePokemonUseCase(pokemon)
        }
    }

    fun getAllPokemon(): LiveData<List<Pokemon>> {
        return getAllPokemonUseCase()
    }
}*/