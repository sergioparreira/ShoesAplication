package com.aplication.shoesaplication.presentation.detalhes.intent

sealed class DetalheItemUiIntent {

    data class IniciarTelaDetalheItem(val idItem: Long) : DetalheItemUiIntent()

    data object OnClickCarrinho : DetalheItemUiIntent()


}