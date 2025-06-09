package com.aplication.shoesaplication.viewmodel

import com.aplication.shoesaplication.di.repositoryModule
import com.aplication.shoesaplication.di.viewModelModule
import com.aplication.shoesaplication.domain.enums.TipoItemEnum
import com.aplication.shoesaplication.domain.model.Item
import com.aplication.shoesaplication.presentation.detalhes.DetalheItemViewModel
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.koin.core.context.GlobalContext.startKoin
import org.koin.mp.KoinPlatform.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class DetalheItemViewModelTest : KoinTest {

    private lateinit var viewModel: DetalheItemViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())

        startKoin {
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule
                )
            )
        }
        viewModel = get()
    }

    @After
    fun tearDown() {
        stopKoin()
    }


    //fluxo teste getItemById
    @Test
    fun `getItemById deve retornar Result success com item correspondente`() = runTest {
        val itemEsperado = Item(
            idItem = 1L,
            nome = "Tênis de Corrida",
            preco = 250.0,
            tipoItem = TipoItemEnum.TENIS,
            nomeImagem = 123,
            description = "Tênis leve para corrida"
        )

        whenever(viewModel.itemRepository.getItem(1L)).thenReturn(itemEsperado)

        val result = viewModel.getItemById(1L)

        assertTrue(result.isSuccess)
        assertEquals(itemEsperado, result.getOrNull())
    }

    @Test
    fun `getItemById deve retornar Result failure ao nao encontrar item`() = runTest {
        val exception = NoSuchElementException("Item não encontrado")

        whenever(viewModel.itemRepository.getItem(99L)).thenThrow(exception)

        val result = viewModel.getItemById(99L)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }


    @Test
    fun `getItemById deve retornar itens diferentes para ids diferentes`() = runTest {
        val item1 = Item(1L, "Tênis", 200.0, TipoItemEnum.TENIS, 123, "Tênis esportivo")
        val item2 = Item(2L, "Bota", 300.0, TipoItemEnum.BOTAS, 124, "Bota de trilha")

        whenever(viewModel.itemRepository.getItem(1L)).thenReturn(item1)
        whenever(viewModel.itemRepository.getItem(2L)).thenReturn(item2)

        val result1 = viewModel.getItemById(1L)
        val result2 = viewModel.getItemById(2L)

        assertEquals(item1, result1.getOrNull())
        assertEquals(item2, result2.getOrNull())
    }

    @Test
    fun `getItemById deve capturar excecao generica em Result failure`() = runTest {
        val exception = RuntimeException("Erro inesperado")

        whenever(viewModel.itemRepository.getItem(3L)).thenThrow(exception)

        val result = viewModel.getItemById(3L)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is RuntimeException)
        assertEquals("Erro inesperado", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getItemById deve retornar item com nome e descricao vazios`() = runTest {
        val item = Item(
            idItem = 4L,
            nome = "",
            preco = 99.99,
            tipoItem = TipoItemEnum.SAPATENIS,
            nomeImagem = 125,
            description = ""
        )

        whenever(viewModel.itemRepository.getItem(4L)).thenReturn(item)

        val result = viewModel.getItemById(4L)

        assertTrue(result.isSuccess)
        assertEquals(item, result.getOrNull())
    }
    @Test
    fun `getItemById deve retornar failure para idItem negativo`() = runTest {
        val exception = IllegalArgumentException("ID inválido")

        whenever(viewModel.itemRepository.getItem(-1L)).thenThrow(exception)

        val result = viewModel.getItemById(-1L)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
        assertEquals("ID inválido", result.exceptionOrNull()?.message)
    }

}