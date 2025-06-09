package com.aplication.shoesaplication.domain.repository

import com.aplication.shoesaplication.domain.model.Item
import com.aplication.shoesaplication.domain.model.ItemFiltro

interface ItemRepository {

    suspend fun getItem(idItem: Long): Item

    suspend fun getListaItem(): List<Item>

    suspend fun getListaFiltro(): List<ItemFiltro>

}