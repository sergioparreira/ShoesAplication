package com.aplication.shoesaplication.domain.model

import com.aplication.shoesaplication.domain.enums.TipoItemEnum

data class ItemFiltro(
    val tipoFiltro: TipoItemEnum,
    val ativo: Boolean,
)
