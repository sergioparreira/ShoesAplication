package com.aplication.shoesaplication.presentation.main.composable

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
import com.aplication.shoesaplication.presentation.composable.ErrorScreen
import com.aplication.shoesaplication.presentation.composable.LoadingScreen
import com.aplication.shoesaplication.presentation.main.intent.MainScreenUiIntent
import com.aplication.shoesaplication.presentation.main.state.ItemCarrinhoUiState


@Composable
fun ItemCarrinhoComposable(
    modifier: Modifier,
    uiState: ItemCarrinhoUiState,
    onIntent: (MainScreenUiIntent) -> Unit
) {
    when (uiState) {
        is ItemCarrinhoUiState.Error -> {
            ErrorScreen()
        }

        ItemCarrinhoUiState.Loading -> {
            LoadingScreen()
        }

        ItemCarrinhoUiState.Idle -> {
            onIntent(MainScreenUiIntent.IniciarCarrinhoScreen)
        }

        is ItemCarrinhoUiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(vertical = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier,
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
