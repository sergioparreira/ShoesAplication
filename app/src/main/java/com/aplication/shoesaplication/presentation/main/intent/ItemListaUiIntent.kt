package com.aplication.shoesaplication.presentation.main.intent

import com.aplication.shoesaplication.domain.enums.TipoItemEnum


sealed class ItemListaUiIntent {

    data class OnSearchQueryChange(val query: String) : ItemListaUiIntent()

    data class OnClickFiltro(val filtroSelecionado: TipoItemEnum) : ItemListaUiIntent()

    data class OnItemClick(val idItem: Long) : ItemListaUiIntent()

}