package com.aplication.maxcomposeshoes.domain.repository

import com.aplication.maxcomposeshoes.domain.model.Item
import com.aplication.maxcomposeshoes.domain.model.ItemFiltro

interface UserRepository {

    suspend fun getNomeUserName(): String

}