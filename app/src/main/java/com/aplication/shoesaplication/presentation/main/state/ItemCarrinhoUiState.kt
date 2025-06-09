package com.aplication.maxcomposeshoes.presentation.main.state


sealed class ItemCarrinhoUiState {

    data object Loading : ItemCarrinhoUiState()

    data class Success(val text: String) : ItemCarrinhoUiState()

    data class Error(val exception: Throwable) : ItemCarrinhoUiState()

    data object Idle : ItemCarrinhoUiState()

}