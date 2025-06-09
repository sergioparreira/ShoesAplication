package com.aplication.maxcomposeshoes.presentation.composable

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

@Composable
fun ErrorScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa largura e altura totais
            .padding(vertical = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Centraliza verticalmente
    ) {
        Text(
            modifier = Modifier,
            text = "Erro ao carregar, contate o suporte",
            fontSize = 40.sp,
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}
