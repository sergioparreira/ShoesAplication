package com.aplication.maxcomposeshoes.presentation.main.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aplication.shoesaplication.R
import com.aplication.maxcomposeshoes.presentation.composable.ErrorScreen
import com.aplication.maxcomposeshoes.presentation.composable.LoadingScreen
import com.aplication.maxcomposeshoes.presentation.main.intent.MainScreenUiIntent
import com.aplication.maxcomposeshoes.presentation.main.state.PerfilUiState

@Composable
fun PerfilScreenComposable(
    modifier: Modifier,
    uiState: PerfilUiState,
    onIntent: (MainScreenUiIntent) -> Unit
) {

    when (uiState) {
        is PerfilUiState.Error -> {
            ErrorScreen()
        }

        PerfilUiState.Loading -> {
            LoadingScreen()
        }

        PerfilUiState.Idle -> {
            onIntent(MainScreenUiIntent.IniciarPerfilScreen)
        }

        is PerfilUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxSize() // Ocupa largura e altura totais
                    .padding(vertical = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center // Centraliza verticalmente
            ) {
                Text(
                    text = uiState.text,
                    fontSize = 40.sp,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_bold)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }


    }

}
