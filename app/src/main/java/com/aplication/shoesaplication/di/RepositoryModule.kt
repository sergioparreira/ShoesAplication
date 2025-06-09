package com.aplication.shoesaplication.di

import com.aplication.shoesaplication.data.ItemRepositoryImpl
import com.aplication.shoesaplication.data.UserRepositoryImpl
import com.aplication.shoesaplication.domain.repository.ItemRepository
import com.aplication.shoesaplication.domain.repository.UserRepository
import org.koin.dsl.module


val repositoryModule = module {
    single<ItemRepository> { ItemRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }
}