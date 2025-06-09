package com.aplication.maxcomposeshoes.domain.repository

import com.aplication.maxcomposeshoes.domain.model.Item
import com.aplication.maxcomposeshoes.domain.model.ItemFiltro

interface ItemRepository {

    suspend fun getItem(idItem: Long): Item

    suspend fun getListaItem(): List<Item>

    suspend fun getListaFiltro(): List<ItemFiltro>

}