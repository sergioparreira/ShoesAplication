package com.aplication.shoesaplication.di

import com.aplication.shoesaplication.presentation.detalhes.DetalheItemViewModel
import com.aplication.shoesaplication.presentation.main.MainScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { MainScreenViewModel(get(), get()) }
    viewModel { DetalheItemViewModel(get()) }
}

