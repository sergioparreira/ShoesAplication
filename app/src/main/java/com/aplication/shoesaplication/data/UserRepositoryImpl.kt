package com.aplication.maxcomposeshoes.data

import com.aplication.maxcomposeshoes.domain.repository.UserRepository

class UserRepositoryImpl : UserRepository {

    override suspend fun getNomeUserName(): String {
        //aqui pode ir no bano ou na api conforme necessario
        return "Cleyton"
    }

}