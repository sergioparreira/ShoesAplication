package com.aplication.shoesaplication.domain.enums

import androidx.annotation.DrawableRes
import com.aplication.shoesaplication.R

enum class OpcaoNavegacao(val label: String, @DrawableRes val icon: Int, val route: String) {
    INICIO("Inicio", R.drawable.home, "inicio"),
    CARRINHO("Carrinho", R.drawable.carro, "carrinho"),
    PERFIL("Perfil", R.drawable.perfil, "perfil")
}