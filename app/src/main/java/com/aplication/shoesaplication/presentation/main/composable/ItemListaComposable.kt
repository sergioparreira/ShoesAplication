package com.aplication.shoesaplication.presentation.main.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aplication.shoesaplication.R
import com.aplication.shoesaplication.domain.model.Item
import com.aplication.shoesaplication.presentation.main.intent.ItemListaUiIntent
import com.aplication.shoesaplication.ui.theme.blackText

@Composable
fun ItemListaComposable(
    modifier: Modifier, produtos: List<Item>, onIntentItemLista: (ItemListaUiIntent) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        content = {
            items(produtos.size) { index ->
                val produto = produtos[index]
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                        .clickable {
                            onIntentItemLista(ItemListaUiIntent.OnItemClick(produto.idItem))
                        },
                    horizontalAlignment = Alignment.Start
                ) {
                    Image(
                        painter = painterResource(id = produto.nomeImagem),
                        contentDescription = produto.nome,
                        modifier = Modifier.width(180.dp).height(180.dp),
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = produto.nome,
                        fontSize = 10.sp,
                        color = colorResource(R.color.placeholder_poppins),
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                            fontWeight = FontWeight.Normal
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = produto.preco.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = blackText,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.poppins_bold)),
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
        }
    )
}
