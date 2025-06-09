package com.aplication.shoesaplication.presentation.main.composable

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aplication.shoesaplication.R
import com.aplication.shoesaplication.domain.enums.TipoItemEnum
import com.aplication.shoesaplication.presentation.composable.ErrorScreen
import com.aplication.shoesaplication.presentation.composable.LoadingScreen
import com.aplication.shoesaplication.presentation.main.intent.ItemListaUiIntent
import com.aplication.shoesaplication.presentation.main.intent.ItemListaUiIntent.OnSearchQueryChange
import com.aplication.shoesaplication.presentation.main.intent.MainScreenUiIntent
import com.aplication.shoesaplication.presentation.main.state.ItemListaUiState
import com.aplication.shoesaplication.ui.theme.blackText
import com.aplication.shoesaplication.ui.theme.bordaColorSearch
import com.aplication.shoesaplication.ui.theme.unfocusedColorSearch


@Composable
fun ListaComposable(
    modifier: Modifier,
    uiState: ItemListaUiState,
    onIntent: (MainScreenUiIntent) -> Unit,
    onIntentItemLista: (ItemListaUiIntent) -> Unit,
    innerPadding: PaddingValues
) {
    when (uiState) {
        is ItemListaUiState.Error -> {
            ErrorScreen()
        }

        ItemListaUiState.Loading -> {
            LoadingScreen()

        }
        ItemListaUiState.Idle -> {
            onIntent(MainScreenUiIntent.IniciarHomeScreen)
        }

        is ItemListaUiState.Success -> {
            val configuration = LocalConfiguration.current
            val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

            Column(modifier = modifier
                .then(
                    if (isLandscape) {
                        Modifier.padding(start = innerPadding.calculateTopPadding(), bottom = innerPadding.calculateBottomPadding())
                    } else {
                        Modifier
                            .fillMaxSize()
                            .padding(
                                bottom = innerPadding.calculateBottomPadding(),
                            )
                    }
                )) {
                Column(
                    modifier = Modifier
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = innerPadding.calculateTopPadding())
                            .padding(top = 10.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "Ola, ${uiState.nomeUsuario}",
                        fontSize = 14.sp,
                        color = blackText,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.poppins_bold)),
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(
                                if (isLandscape) {
                                    Modifier
                                        .padding(start = 16.dp, end = 16.dp)
                                        .navigationBarsPadding()
                                } else {
                                    Modifier.padding(16.dp)
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = uiState.filtroSearch,
                            onValueChange = {
                                onIntentItemLista(OnSearchQueryChange(it))
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = unfocusedColorSearch,
                                unfocusedBorderColor = bordaColorSearch
                            ),
                            placeholder = {
                                Text(
                                    modifier = Modifier.padding(start = 8.dp),
                                    text = stringResource(R.string.pesquisar),
                                    color = colorResource(R.color.placeholder_poppins),
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal,
                                    )
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(55.dp),
                            shape = RoundedCornerShape(16.dp),
                        )

                        Image(
                            painter = painterResource(id = R.drawable.svgiconsearch), // seu ícone de busca
                            contentDescription = "search",
                            modifier = Modifier
                                .height(60.dp)
                                .clickable {
                                    onIntentItemLista(OnSearchQueryChange(uiState.filtroSearch))
                                }
                        )
                    }

                    ListaFiltroComposable(uiState.listaFiltro, onIntentItemLista, isLandscape)
                }

                if (uiState.listaItemsFiltrada.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        val tipoFiltro = uiState.listaFiltro.find { it.ativo }?.tipoFiltro
                        val descricao =
                            if (tipoFiltro == TipoItemEnum.TODOS) "" else  "em ${tipoFiltro?.descricao}"

                        Text(
                            text = "Nada para exibir $descricao",
                            fontSize = 20.sp,
                            color = blackText,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }

                } else {
                    ItemListaComposable(
                        Modifier
                            .fillMaxSize()
                            .padding(
                                top = 16.dp,
                                start = 16.dp,
                                end = 16.dp
                            ),
                        uiState.listaItemsFiltrada, onIntentItemLista
                    )
                }

            }

        }

    }
}



