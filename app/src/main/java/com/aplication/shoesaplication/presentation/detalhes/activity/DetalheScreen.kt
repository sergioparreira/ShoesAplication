package com.aplication.maxcomposeshoes.presentation.detalhes.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.aplication.maxcomposeshoes.presentation.detalhes.DetalheItemViewModel
import com.aplication.maxcomposeshoes.presentation.detalhes.composable.OnDetalharItemScreenState
import com.aplication.maxcomposeshoes.presentation.detalhes.effect.DetalharEntregaUiffect
import com.aplication.maxcomposeshoes.presentation.detalhes.intent.DetalheItemUiIntent
import com.aplication.maxcomposeshoes.ui.theme.MaxComposeShoesTheme
import org.koin.androidx.compose.koinViewModel

class DetalheScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            val mViewModel: DetalheItemViewModel = koinViewModel()
            val uiStateDetalheEnt by mViewModel.uiStateDetalheItem.collectAsState()

            RegistrarUiEffect(mViewModel)

            MaxComposeShoesTheme {
                OnDetalharItemScreenState(
                    modifier = Modifier.fillMaxSize(),
                    uiStateDetalheEnt,
                    onIntentDetalhes = { mViewModel.onIntent(it) },
                    intent.getLongExtra("idItem", 0)
                )
            }
        }
    }
    @Composable
    private fun RegistrarUiEffect(mViewModel: DetalheItemViewModel) {
        LaunchedEffect(Unit) {
            mViewModel.uiEffect.collect { effect ->
                when (effect) {
                    DetalharEntregaUiffect.FecharTela -> {
                        val resultIntent = Intent().apply {
                            putExtra("is_redirecionar", true)
                        }
                        setResult(RESULT_OK, resultIntent)
                        finish()
                    }
                }
            }
        }
    }
}
