package com.aplication.shoesaplication.presentation.detalhes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aplication.shoesaplication.domain.model.Item
import com.aplication.shoesaplication.domain.repository.ItemRepository
import com.aplication.shoesaplication.presentation.detalhes.effect.DetalharEntregaUiffect
import com.aplication.shoesaplication.presentation.detalhes.intent.DetalheItemUiIntent
import com.aplication.shoesaplication.presentation.detalhes.state.DetalheItemUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalheItemViewModel(internal val itemRepository: ItemRepository) : ViewModel() {

    private val _uiStateDetalheItem =
        MutableStateFlow<DetalheItemUiState>(DetalheItemUiState.Idle)
    val uiStateDetalheItem: StateFlow<DetalheItemUiState> = _uiStateDetalheItem.asStateFlow()

    private val _uiEffect = MutableSharedFlow<DetalharEntregaUiffect>()
    val uiEffect = _uiEffect.asSharedFlow()


    fun onIntent(intent: DetalheItemUiIntent) {
        when (intent) {
            is DetalheItemUiIntent.IniciarTelaDetalheItem -> {
                iniciarTelaDetalheItem(intent.idItem)
            }

            DetalheItemUiIntent.OnClickCarrinho -> {
                fecharTelaDetalhes()
            }
        }
    }

    private fun fecharTelaDetalhes() {
        viewModelScope.launch {
            _uiEffect.emit(DetalharEntregaUiffect.FecharTela)
        }
    }

    private fun iniciarTelaDetalheItem(idItem: Long) {
        viewModelScope.launch {
            val ret = getItemById(idItem)

            ret.onSuccess { item ->
                _uiStateDetalheItem.update {
                    DetalheItemUiState.Success(item)
                }

            }.onFailure { trow ->
                _uiStateDetalheItem.update {
                    DetalheItemUiState.Error(trow)
                }

            }
        }
    }

    internal suspend fun getItemById(idItem: Long): Result<Item> = withContext(Dispatchers.IO) {
        try {
            val item = itemRepository.getItem(idItem)
            Result.success(item)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}