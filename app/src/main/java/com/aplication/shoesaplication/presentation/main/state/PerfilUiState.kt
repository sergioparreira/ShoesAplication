package com.aplication.maxcomposeshoes.presentation.main.state


sealed class PerfilUiState {

    data object Loading : PerfilUiState()

    data class Success(val text: String) : PerfilUiState()

    data class Error(val exception: Throwable) : PerfilUiState()

    data object Idle : PerfilUiState()

}