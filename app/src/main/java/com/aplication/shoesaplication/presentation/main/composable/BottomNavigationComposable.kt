package com.aplication.maxcomposeshoes.presentation.main.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.aplication.shoesaplication.domain.enums.OpcaoNavegacao
import com.aplication.maxcomposeshoes.presentation.main.intent.MainScreenUiIntent
import com.aplication.maxcomposeshoes.ui.theme.ItemNaoSelecionado
import com.aplication.maxcomposeshoes.ui.theme.ItemSelecionado
import com.aplication.maxcomposeshoes.ui.theme.LocalExtraColors
import com.aplication.maxcomposeshoes.ui.theme.dividerColor
import com.aplication.maxcomposeshoes.ui.theme.transparente


@Composable
fun BottomNavigationComposable(
    navController: NavHostController,
    opcoesDeNavegacao: List<OpcaoNavegacao>,
    onIntent: (MainScreenUiIntent) -> Unit,
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    Column {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = dividerColor
        )

        NavigationBar(
            containerColor = LocalExtraColors.current.backgroundScreen
        ) {
            opcoesDeNavegacao.forEach { item ->
                val itemSelecionado = item.route == currentDestination?.route
                val colorText = if (itemSelecionado) ItemSelecionado else ItemNaoSelecionado

                NavigationBarItem(
                    selected = itemSelecionado,
                    onClick = {
                        onIntent(MainScreenUiIntent.AlterarAbaSelecionada(item))
                    },
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.label,
                                tint = colorText
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.label,
                                color = colorText,
                                fontSize = 14.sp,
                                letterSpacing = 0.2.sp,
                            )
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = transparente,
                        selectedIconColor = ItemSelecionado,
                        unselectedIconColor = ItemNaoSelecionado
                    )
                )
            }
        }
    }

}