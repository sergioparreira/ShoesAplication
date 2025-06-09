package com.aplication.shoesaplication.presentation.detalhes.state

import com.aplication.shoesaplication.domain.model.Item


sealed class DetalheItemUiState {

    data object Loading : DetalheItemUiState()

    data class Success(val item: Item) : DetalheItemUiState()

    data class Error(val exception: Throwable) : DetalheItemUiState()

    data object Idle : DetalheItemUiState()

}