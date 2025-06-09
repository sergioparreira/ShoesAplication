package com.aplication.maxcomposeshoes.domain.model

import com.aplication.maxcomposeshoes.domain.enums.TipoItemEnum

data class ItemFiltro(
    val tipoFiltro: TipoItemEnum,
    val ativo: Boolean,
)
