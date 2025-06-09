package com.aplication.maxcomposeshoes.presentation.main.state

import com.aplication.maxcomposeshoes.domain.model.Item
import com.aplication.maxcomposeshoes.domain.model.ItemFiltro

sealed class ItemListaUiState {

    data object Loading : ItemListaUiState()

    data class Success(
        val nomeUsuario: String,
        val filtroSearch: String = "",
        val listaItems: List<Item>,
        val listaItemsFiltrada: List<Item>,
        val listaFiltro: List<ItemFiltro>
    ) : ItemListaUiState()

    data class Error(val exception: Throwable) : ItemListaUiState()

    data object Idle : ItemListaUiState()

}
