package com.aplication.maxcomposeshoes.di

import com.aplication.maxcomposeshoes.data.ItemRepositoryImpl
import com.aplication.maxcomposeshoes.data.UserRepositoryImpl
import com.aplication.maxcomposeshoes.domain.repository.ItemRepository
import com.aplication.maxcomposeshoes.domain.repository.UserRepository
import org.koin.dsl.module


val repositoryModule = module {
    single<ItemRepository> { ItemRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }
}