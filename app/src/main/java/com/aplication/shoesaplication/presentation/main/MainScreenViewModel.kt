package com.aplication.maxcomposeshoes.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aplication.shoesaplication.domain.enums.OpcaoNavegacao
import com.aplication.maxcomposeshoes.domain.enums.TipoItemEnum
import com.aplication.maxcomposeshoes.domain.model.Item
import com.aplication.maxcomposeshoes.domain.model.ItemFiltro
import com.aplication.maxcomposeshoes.domain.repository.ItemRepository
import com.aplication.maxcomposeshoes.domain.repository.UserRepository
import com.aplication.maxcomposeshoes.presentation.main.effect.ItemListaUiEffect
import com.aplication.maxcomposeshoes.presentation.main.intent.ItemListaUiIntent
import com.aplication.maxcomposeshoes.presentation.main.intent.MainScreenUiIntent
import com.aplication.maxcomposeshoes.presentation.main.state.ItemCarrinhoUiState
import com.aplication.maxcomposeshoes.presentation.main.state.ItemListaUiState
import com.aplication.maxcomposeshoes.presentation.main.state.MainSreenState
import com.aplication.maxcomposeshoes.presentation.main.state.PerfilUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainScreenViewModel(
    internal val itemRepository: ItemRepository,
    internal val userRepository: UserRepository
) : ViewModel() {

    private val _uiStateItemLista = MutableStateFlow<ItemListaUiState>(ItemListaUiState.Idle)
    val uiStateItemLista: StateFlow<ItemListaUiState> = _uiStateItemLista.asStateFlow()

    private val _uiStateItemCarrinho =
        MutableStateFlow<ItemCarrinhoUiState>(ItemCarrinhoUiState.Idle)
    val uiStateItemCarrinho: StateFlow<ItemCarrinhoUiState> = _uiStateItemCarrinho.asStateFlow()

    private val _uiStatePerfil = MutableStateFlow<PerfilUiState>(PerfilUiState.Idle)
    val uiStatePerfil: StateFlow<PerfilUiState> = _uiStatePerfil.asStateFlow()

    private val _mainScreenState = MutableStateFlow<MainSreenState>(MainSreenState.Idle)
    val mainScreenState: StateFlow<MainSreenState> = _mainScreenState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<ItemListaUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun onIntent(intent: MainScreenUiIntent) {
        when (intent) {
            MainScreenUiIntent.IniciarMainScreen -> {
                iniciarMainScreen()
            }

            MainScreenUiIntent.IniciarHomeScreen -> {
                iniciarItemListaState()
            }

            MainScreenUiIntent.IniciarCarrinhoScreen -> {
                iniciarItemCarrinhoState()
            }

            MainScreenUiIntent.IniciarPerfilScreen -> {
                iniciarPerfilState()
            }

            is MainScreenUiIntent.AlterarAbaSelecionada -> {
                alterarAbaSelecionada(intent.routeSelected)
            }
        }

    }

    private fun alterarAbaSelecionada(abaSelecionada: OpcaoNavegacao){
        viewModelScope.launch {

            when(val state = mainScreenState.value){
                is MainSreenState.Success -> {
                    _mainScreenState.update {
                        state.copy(routeSelected = abaSelecionada)
                    }
                }
                else -> {}
            }
        }

    }

    fun onIntent(intent: ItemListaUiIntent) {
        when (intent) {
            is ItemListaUiIntent.OnSearchQueryChange -> {
                filtrarListaQuery(intent.query)
            }

            is ItemListaUiIntent.OnClickFiltro -> {
                filtrarListaTipo(intent)
            }

            is ItemListaUiIntent.OnItemClick -> {
                onItemClick(intent.idItem)

            }
        }
    }

    private fun onItemClick(idItem: Long){
        viewModelScope.launch {
            _uiEffect.emit(ItemListaUiEffect.NavegarTelaDetalhes(idItem))

        }
    }

    private fun iniciarMainScreen() {
        viewModelScope.launch {
            _mainScreenState.update {
                MainSreenState.Loading
            }
            _mainScreenState.update {
                MainSreenState.Success(
                    OpcaoNavegacao.INICIO,
                    listOf(
                        OpcaoNavegacao.INICIO,
                        OpcaoNavegacao.CARRINHO,
                        OpcaoNavegacao.PERFIL
                    )
                )
            }
        }
    }

    private fun iniciarItemListaState() {
        viewModelScope.launch {
            _uiStateItemLista.update {
                ItemListaUiState.Loading
            }
            val itemListaFiltroResult = getListaFiltro()
            lateinit var itemListaFiltro: List<ItemFiltro>

            //se necessario pode criar um state para erros no filtro, mas por eqnaunto so passamos uma lista vazia
            itemListaFiltroResult.onSuccess {
                itemListaFiltro = it
            }.onFailure {
                itemListaFiltro = mutableListOf()
            }

            lateinit var nomeUsuario: String
            val nomeUserResult = getNomeUsuario()

            nomeUserResult.onSuccess {
                nomeUsuario = it
            }.onFailure {
                nomeUsuario = "Desconhecido"
            }

            val itemListaResut = getListaItem()

            itemListaResut.onSuccess { listaItem ->
                _uiStateItemLista.update {
                    ItemListaUiState.Success(
                        nomeUsuario,
                        filtroSearch = "",
                        listaItem,
                        listaItem,
                        itemListaFiltro,
                    )
                }
            }.onFailure { error ->
                _uiStateItemLista.update {
                    ItemListaUiState.Error(error)
                }
            }
        }
    }

    private fun filtrarListaQuery(query: String) {
        viewModelScope.launch {
            when (val state = uiStateItemLista.value) {
                is ItemListaUiState.Success -> {
                    val getListaFiltrada = getListaFiltradaQuery(
                        state.listaItems,
                        query,
                        state.listaFiltro.find { it.ativo }?.tipoFiltro ?: TipoItemEnum.TODOS
                    )

                    getListaFiltrada.onSuccess { listaFilter ->
                        _uiStateItemLista.update {
                            state.copy(
                                listaItemsFiltrada = listaFilter,
                                filtroSearch = query
                            )
                        }

                    }.onFailure { error ->
                        _uiStateItemLista.update {
                            ItemListaUiState.Error(error)
                        }

                    }
                }

                else -> {}
            }
        }
    }

    private fun filtrarListaTipo(intent: ItemListaUiIntent.OnClickFiltro) {
        viewModelScope.launch {
            when (val state = uiStateItemLista.value) {
                is ItemListaUiState.Success -> {
                    //define o item filtro ativo
                    val listaFiltro = state.listaFiltro.map { itemFiltro ->
                        if (itemFiltro.tipoFiltro == intent.filtroSelecionado) {
                            itemFiltro.copy(ativo = true)
                        } else {
                            itemFiltro.copy(ativo = false)
                        }
                    }


                    val getListaFiltrada = getListaFiltradaQuery(
                        state.listaItems,
                        state.filtroSearch,
                        intent.filtroSelecionado
                    )


                    getListaFiltrada.onSuccess { listaFilter ->
                        _uiStateItemLista.update {
                            state.copy(
                                listaItemsFiltrada = listaFilter,
                                listaFiltro = listaFiltro
                            )
                        }
                    }.onFailure { error ->
                        _uiStateItemLista.update {
                            ItemListaUiState.Error(error)
                        }

                    }
                }

                else -> {}

            }
        }
    }

    internal fun getListaFiltradaQuery(
        listaOriginal: List<Item>,
        filtroSearch: String,
        tipoItemSelec: TipoItemEnum
    ): Result<List<Item>> {
        return try {
            val listaFiltrada = listaOriginal
                .filter {
                    (it.tipoItem == tipoItemSelec
                            || tipoItemSelec == TipoItemEnum.TODOS) && (it.nome.contains(
                        filtroSearch, ignoreCase = true
                    ) || filtroSearch.isEmpty())
                }

            Result.success(listaFiltrada)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    internal suspend fun getListaItem(): Result<List<Item>> = withContext(Dispatchers.IO) {
        try {
            val itemListaItem = itemRepository.getListaItem()
            Result.success(itemListaItem)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    internal suspend fun getNomeUsuario(): Result<String> = withContext(Dispatchers.IO) {
        try {
            val nomeUsuario = userRepository.getNomeUserName()
            Result.success(nomeUsuario)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    internal suspend fun getListaFiltro(): Result<List<ItemFiltro>> = withContext(Dispatchers.IO) {
        try {
            val itemListaFiltro = itemRepository.getListaFiltro()
            Result.success(itemListaFiltro)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun iniciarItemCarrinhoState() {
        viewModelScope.launch {
            _uiStateItemCarrinho.update {
                ItemCarrinhoUiState.Success("Carrinho")
            }
        }
    }

    private fun iniciarPerfilState() {
        viewModelScope.launch {
            _uiStatePerfil.update {
                PerfilUiState.Success("Perfil")
            }
        }
    }

}