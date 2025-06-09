package com.aplication.maxcomposeshoes.di

import com.aplication.maxcomposeshoes.presentation.detalhes.DetalheItemViewModel
import com.aplication.maxcomposeshoes.presentation.main.MainScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { MainScreenViewModel(get(), get()) }
    viewModel { DetalheItemViewModel(get()) }
}

