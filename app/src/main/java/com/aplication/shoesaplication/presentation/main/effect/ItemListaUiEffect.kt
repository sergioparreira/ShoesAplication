package com.aplication.maxcomposeshoes.presentation.main.effect

sealed class ItemListaUiEffect {

    data class NavegarTelaDetalhes(val idItem: Long) : ItemListaUiEffect()

}