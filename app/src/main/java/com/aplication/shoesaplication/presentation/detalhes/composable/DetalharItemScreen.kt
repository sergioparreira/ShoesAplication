package com.aplication.shoesaplication.presentation.detalhes.composable

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aplication.shoesaplication.R

import androidx.compose.ui.unit.sp
import com.aplication.shoesaplication.presentation.detalhes.intent.DetalheItemUiIntent
import com.aplication.shoesaplication.presentation.detalhes.state.DetalheItemUiState
import com.aplication.shoesaplication.ui.theme.ItemSelecionado
import com.aplication.shoesaplication.ui.theme.LocalExtraColors
import com.aplication.shoesaplication.ui.theme.amareloStart
import com.aplication.shoesaplication.ui.theme.blackText
import com.aplication.shoesaplication.ui.theme.branco
import com.aplication.shoesaplication.ui.theme.cinzaAvalicao
import com.aplication.shoesaplication.ui.theme.cinzaCoracao

@Composable
fun DetalharItemScreen(
    modifier: Modifier,
    mDetalharItemUiState: DetalheItemUiState.Success,
    innerPadding: PaddingValues, onIntentDetalhes : (DetalheItemUiIntent) -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Box(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(LocalExtraColors.current.backgroundScreen)
    ) {

        // Conteúdo principal com Scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp)
                .then(
                    if (isLandscape) {
                        Modifier.padding(start = innerPadding.calculateTopPadding())
                    } else {
                        Modifier
                    }
                )
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = mDetalharItemUiState.item.nomeImagem),
                contentDescription = mDetalharItemUiState.item.nome,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .then(
                        if (isLandscape) {
                            Modifier
                                .width(250.dp)
                                .height(250.dp)
                        } else {
                            Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                        }
                    ),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (isLandscape) {
                            Modifier.padding(start = innerPadding.calculateTopPadding())
                        } else {
                            Modifier.padding(horizontal = 16.dp)
                        }
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = mDetalharItemUiState.item.nome,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = blackText,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_bold)),
                        fontSize = 16.sp
                    )
                )

                Icon(
                    painter = painterResource(id = R.drawable.sbgcoracao),
                    contentDescription = "favorite",
                    tint = cinzaCoracao
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                repeat(5) {
                    val resourc = if (it == 4) R.drawable.halfstart else R.drawable.svgstart
                    Icon(
                        painter = painterResource(id = resourc),
                        contentDescription = "favorite",
                        tint = amareloStart
                    )
                }

                Text(
                    text = "(10)",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = cinzaAvalicao,
                    modifier = Modifier.padding(start = 5.dp),
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_bold)), fontSize = 12.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = mDetalharItemUiState.item.description,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = cinzaAvalicao,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontSize = 16.sp
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = mDetalharItemUiState.item.preco.toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = blackText,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 16.sp
                )
            )
        }

        //conteudo botoes
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(55.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, ItemSelecionado),
                    color = ItemSelecionado,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onIntentDetalhes(
                                DetalheItemUiIntent.OnClickCarrinho
                            )
                        }) {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Adicionar ao carrinho", color = branco, style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.svgcarbuy),
                    contentDescription = "search",
                    modifier = Modifier
                        .height(60.dp)
                        .clickable {
                            onIntentDetalhes(
                                DetalheItemUiIntent.OnClickCarrinho
                            )
                        })
            }
        }
        // Botões no fundo da tela

    }
}
