package com.aplication.shoesaplication.domain.model

import androidx.annotation.DrawableRes
import com.aplication.shoesaplication.domain.enums.TipoItemEnum

data class Item(
    val idItem: Long,
    val nome: String,
    val preco: Double,
    val tipoItem: TipoItemEnum,
    @DrawableRes val nomeImagem: Int,
    val description: String
)
