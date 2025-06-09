package com.aplication.maxcomposeshoes.presentation.detalhes.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aplication.maxcomposeshoes.presentation.composable.ErrorScreen
import com.aplication.maxcomposeshoes.presentation.composable.LoadingScreen
import com.aplication.maxcomposeshoes.presentation.detalhes.intent.DetalheItemUiIntent
import com.aplication.maxcomposeshoes.presentation.detalhes.state.DetalheItemUiState
import com.aplication.maxcomposeshoes.ui.theme.LocalExtraColors

@Composable
fun OnDetalharItemScreenState(
    modifier: Modifier,
    detalheItemUiState: DetalheItemUiState,
    onIntentDetalhes: (DetalheItemUiIntent) -> Unit,
    idItem: Long = 0
) {
        Scaffold(
            modifier = modifier.background(LocalExtraColors.current.backgroundScreen),
        ) { innerPadding ->
            when (detalheItemUiState) {

                is DetalheItemUiState.Error -> {
                    ErrorScreen()
                }

                DetalheItemUiState.Loading -> {
                    LoadingScreen(modifier.padding(innerPadding))

                }

                is DetalheItemUiState.Success -> {
                    DetalharItemScreen(modifier, detalheItemUiState, innerPadding, onIntentDetalhes)
                }

                DetalheItemUiState.Idle -> {
                    onIntentDetalhes(DetalheItemUiIntent.IniciarTelaDetalheItem(idItem))
                }
            }
        }


}