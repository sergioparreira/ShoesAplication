package com.aplication.shoesaplication.presentation.main.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.aplication.shoesaplication.domain.enums.OpcaoNavegacao
import com.aplication.shoesaplication.presentation.detalhes.activity.DetalheScreen
import com.aplication.shoesaplication.presentation.main.MainScreenViewModel
import com.aplication.shoesaplication.presentation.main.composable.MainScreenComposable
import com.aplication.shoesaplication.presentation.main.effect.ItemListaUiEffect
import com.aplication.shoesaplication.presentation.main.intent.MainScreenUiIntent
import com.aplication.shoesaplication.ui.theme.MaxShoesTheme
import org.koin.androidx.compose.koinViewModel

class MainScreen : ComponentActivity() {

    private var launcuer: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()

                val mViewModel: MainScreenViewModel = koinViewModel()
            val mainScreenUiStateFlow by mViewModel.mainScreenState.collectAsState()
            val uiStatePerfil by mViewModel.uiStatePerfil.collectAsState()
            val uiStateItemLista by mViewModel.uiStateItemLista.collectAsState()
            val uiStateItemCarrinho by mViewModel.uiStateItemCarrinho.collectAsState()

            RegistrarUiEffect(mViewModel)
            RegistrarLaunchers(mViewModel)

            val navController = rememberNavController()

            MaxShoesTheme {
                    MainScreenComposable(
                        mainScreenUiStateFlow,
                        uiStateItemLista,
                        uiStateItemCarrinho,
                        uiStatePerfil,
                        onIntent = { intent -> mViewModel.onIntent(intent) },
                        onIntentItemLista = { intent -> mViewModel.onIntent(intent) },
                        navController
                    )
            }

        }
    }

    @Composable
    private fun RegistrarLaunchers(mViewModel: MainScreenViewModel) {
        launcuer = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val isRedirecionar = result.data?.getBooleanExtra("is_redirecionar", false) == true
                if (isRedirecionar) {
                    mViewModel.onIntent(MainScreenUiIntent.AlterarAbaSelecionada(OpcaoNavegacao.CARRINHO))
                }

            }
        }
    }

    @Composable
    private fun RegistrarUiEffect(mViewModel: MainScreenViewModel) {
        LaunchedEffect(Unit) {
            mViewModel.uiEffect.collect { effect ->
                when (effect) {
                    is ItemListaUiEffect.NavegarTelaDetalhes -> {
                        val intent = Intent(this@MainScreen, DetalheScreen::class.java).apply {
                            putExtra("idItem", effect.idItem)
                        }
                        launcuer?.launch(intent)
                    }
                }
            }
        }
    }
}
