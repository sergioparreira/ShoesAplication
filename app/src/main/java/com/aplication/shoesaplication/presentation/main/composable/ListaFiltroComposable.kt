package com.aplication.shoesaplication.presentation.main.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
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
import com.aplication.shoesaplication.domain.model.ItemFiltro
import com.aplication.shoesaplication.presentation.main.intent.ItemListaUiIntent
import com.aplication.shoesaplication.ui.theme.ItemSelecionado
import com.aplication.shoesaplication.ui.theme.bordarFiltro
import com.aplication.shoesaplication.ui.theme.branco
import com.aplication.shoesaplication.ui.theme.textColor

@Composable
fun ListaFiltroComposable(
    itens: List<ItemFiltro>,
    onIntentItemLista: (ItemListaUiIntent) -> Unit,
    isLandscape: Boolean = false
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isLandscape) {
                    Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp)
                } else {
                    Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                }
            ),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(itens) { item ->
            Surface(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    width = 1.dp, color = if (item.ativo) ItemSelecionado else bordarFiltro
                ),
                color = if (item.ativo) ItemSelecionado else branco,
                modifier = Modifier
                    .height(35.dp)
                    .clickable {
                        onIntentItemLista(ItemListaUiIntent.OnClickFiltro(item.tipoFiltro))
                    }) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(horizontal = 25.dp)
                ) {
                    Text(
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        text = item.tipoFiltro.descricao,
                        color = if (item.ativo) branco else textColor
                    )
               }
            }
        }
    }
}