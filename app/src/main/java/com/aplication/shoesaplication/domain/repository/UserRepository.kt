package com.aplication.shoesaplication.domain.repository

interface UserRepository {

    suspend fun getNomeUserName(): String

}