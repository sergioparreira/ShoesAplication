package com.aplication.maxcomposeshoes.presentation.main.intent

import com.aplication.shoesaplication.domain.enums.OpcaoNavegacao


sealed class MainScreenUiIntent {

    data object IniciarMainScreen : MainScreenUiIntent()

    data object IniciarHomeScreen : MainScreenUiIntent()

    data object IniciarCarrinhoScreen : MainScreenUiIntent()

    data object IniciarPerfilScreen : MainScreenUiIntent()

    data class AlterarAbaSelecionada(val routeSelected: OpcaoNavegacao) : MainScreenUiIntent()

}