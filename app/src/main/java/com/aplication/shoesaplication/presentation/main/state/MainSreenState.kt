package com.aplication.maxcomposeshoes.presentation.main.state

import com.aplication.shoesaplication.domain.enums.OpcaoNavegacao

sealed class MainSreenState {

    data object Loading : MainSreenState()

    data class Success(
        val routeSelected: OpcaoNavegacao,
        val opcoesDeNavegacao: List<OpcaoNavegacao>
    ) : MainSreenState()

    data class Error(val exception: Throwable) : MainSreenState()

    data object Idle : MainSreenState()

}