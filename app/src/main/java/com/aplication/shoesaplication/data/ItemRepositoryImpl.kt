package com.aplication.shoesaplication.data

import com.aplication.shoesaplication.domain.enums.TipoItemEnum
import com.aplication.shoesaplication.domain.model.Item
import com.aplication.shoesaplication.domain.model.ItemFiltro
import com.aplication.shoesaplication.domain.repository.ItemRepository
import com.aplication.shoesaplication.R

class ItemRepositoryImpl : ItemRepository {
    override suspend fun getItem(idItem: Long): Item {
        return getListaItem().find { it.idItem == idItem } ?: throw Exception("Item não encontrado")
    }

    override suspend fun getListaItem(): List<Item> {
        val descrip =
            "\"Dê as boas-vindas à próxima geração do Air Max. Este modelo contemporâneo e deslumbrante homenageia o rico legado do Air Max com um cabedal em mesh multicamadas com estampa tátil e iconografia tradicional, detalhes foscos para um visual magnético e um inovador sistema de unidade Dynamic Air, projetado para fazer com que caminhar pareça deslizar no ar.\""
        val itemLista = listOf(
            Item(
                1,
                "Chuteira Nike Tiempo 10",
                245.99,
                TipoItemEnum.CHUTEIRA,
                R.drawable.chuteira_preta_png,
                descrip
            ),
            Item(
                2,
                "Nike Air Max Dn Essential",
                699.00,
                TipoItemEnum.TENIS,
                R.drawable.nike_air_max_dn_essential,
                descrip
            ),
            Item(
                3,
                "Nike Air Max 2013",
                920.00,
                TipoItemEnum.TENIS,
                R.drawable.nike_air_max_2013,
                descrip
            ),
            Item(
                4,
                "Nike Air Zoom Upturn SC",
                399.99,
                TipoItemEnum.TENIS,
                R.drawable.nike_air_zoom_upturn_sc,
                descrip
            )
        )
        return itemLista
    }

    override suspend fun getListaFiltro(): List<ItemFiltro> {
        val itemListaFiltro = listOf(
            ItemFiltro(TipoItemEnum.TODOS, true),
            ItemFiltro(TipoItemEnum.TENIS, false),
            ItemFiltro(TipoItemEnum.BOTAS, false),
            ItemFiltro(TipoItemEnum.CHUTEIRA, false),
            ItemFiltro(TipoItemEnum.SAPATENIS, false)
        )

        return itemListaFiltro
    }


}