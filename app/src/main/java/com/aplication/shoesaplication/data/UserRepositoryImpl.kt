package com.aplication.shoesaplication.data

import com.aplication.shoesaplication.domain.repository.UserRepository

class UserRepositoryImpl : UserRepository {

    override suspend fun getNomeUserName(): String {
        //aqui pode ir no bano ou na api conforme necessario
        return "Cleyton"
    }

}