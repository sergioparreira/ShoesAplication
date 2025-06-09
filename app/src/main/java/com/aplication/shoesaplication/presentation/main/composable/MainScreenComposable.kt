package com.aplication.shoesaplication.presentation.main.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.aplication.shoesaplication.domain.enums.OpcaoNavegacao
import com.aplication.shoesaplication.presentation.composable.ErrorScreen
import com.aplication.shoesaplication.presentation.composable.LoadingScreen
import com.aplication.shoesaplication.presentation.main.intent.ItemListaUiIntent
import com.aplication.shoesaplication.presentation.main.intent.MainScreenUiIntent
import com.aplication.shoesaplication.presentation.main.state.ItemCarrinhoUiState
import com.aplication.shoesaplication.presentation.main.state.ItemListaUiState
import com.aplication.shoesaplication.presentation.main.state.MainSreenState
import com.aplication.shoesaplication.presentation.main.state.PerfilUiState
import com.aplication.shoesaplication.ui.theme.LocalExtraColors


@Composable
fun MainScreenComposable(
    uiState: MainSreenState,
    itemListaUiState: ItemListaUiState,
    itemCarrinhoUiState: ItemCarrinhoUiState,
    perfilUiState: PerfilUiState,
    onIntent: (MainScreenUiIntent) -> Unit,
    onIntentItemLista: (ItemListaUiIntent) -> Unit,
    navController: NavHostController
) {
            when (uiState) {
                is MainSreenState.Error -> {
                    ErrorScreen()
                }

                MainSreenState.Loading -> {
                    LoadingScreen(Modifier)
                }

                MainSreenState.Idle -> {
                    LoadingScreen(Modifier)
                    onIntent(MainScreenUiIntent.IniciarMainScreen)
                }

                is MainSreenState.Success -> {
                    val currentRoute =
                        navController.currentBackStackEntryAsState().value?.destination?.route
                    LaunchedEffect(uiState.routeSelected.route) {
                        if (currentRoute != uiState.routeSelected.route) {
                            navController.navigate(uiState.routeSelected.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }

                    Scaffold(
                        modifier = Modifier.background(LocalExtraColors.current.backgroundScreen),
                        bottomBar = {
                            BottomNavigationComposable(
                                navController,
                                uiState.opcoesDeNavegacao,
                                onIntent,
                            )
                        }) { innerPadding ->

                        NavigationGraph(
                            Modifier.background(LocalExtraColors.current.backgroundScreen),
                            navController = navController,
                            uiStateItemLista = itemListaUiState,
                            uiStateItemCarrinho = itemCarrinhoUiState,
                            uiStatePerfil = perfilUiState,
                            onIntent,
                            onIntentItemLista, innerPadding,
                        )
                    }
                }
            }
}


@Composable
private fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    uiStateItemLista: ItemListaUiState,
    uiStateItemCarrinho: ItemCarrinhoUiState,
    uiStatePerfil: PerfilUiState,
    onIntent: (MainScreenUiIntent) -> Unit,
    onIntentItemLista: (ItemListaUiIntent) -> Unit,
    innerPadding: PaddingValues,
) {
    NavHost(
        navController,
        startDestination = OpcaoNavegacao.INICIO.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(OpcaoNavegacao.INICIO.route) {
            ListaComposable(modifier, uiStateItemLista, onIntent, onIntentItemLista, innerPadding)
        }
        composable(OpcaoNavegacao.CARRINHO.route) {
            ItemCarrinhoComposable(modifier, uiStateItemCarrinho, onIntent)
        }
        composable(OpcaoNavegacao.PERFIL.route) {
            PerfilScreenComposable(modifier, uiStatePerfil, onIntent)
        }

    }
}

