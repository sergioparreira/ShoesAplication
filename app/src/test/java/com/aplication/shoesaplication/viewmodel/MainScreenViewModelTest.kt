package com.aplication.shoesaplication.viewmodel

import com.aplication.shoesaplication.di.repositoryModule
import com.aplication.shoesaplication.di.viewModelModule
import com.aplication.shoesaplication.domain.enums.TipoItemEnum
import com.aplication.shoesaplication.domain.model.Item
import com.aplication.shoesaplication.domain.model.ItemFiltro
import com.aplication.shoesaplication.presentation.main.MainScreenViewModel
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
import java.io.IOException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


@OptIn(ExperimentalCoroutinesApi::class)
class MainScreenViewModelTestt : KoinTest {

    private lateinit var viewModel: MainScreenViewModel


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


    //Teste de filtro enum
    @Test
    fun `getListaFiltro deve retornar Result success com todos os filtros do enum`() = runTest {
        val filtrosEsperados = TipoItemEnum.entries.map {
            ItemFiltro(tipoFiltro = it, ativo = true)
        }
        whenever(viewModel.itemRepository.getListaFiltro()).thenReturn(filtrosEsperados)

        val result = viewModel.getListaFiltro()

        assertTrue(result.isSuccess)
        assertEquals(filtrosEsperados, result.getOrNull())
    }


    @Test
    fun `getListaFiltro deve retornar lista vazia se repositório retornar vazio`() = runTest {
        val filtrosEsperados = emptyList<ItemFiltro>()
        whenever(viewModel.itemRepository.getListaFiltro()).thenReturn(filtrosEsperados)

        val result = viewModel.getListaFiltro()

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }

    @Test
    fun `getListaFiltro deve retornar Result failure se repositório lançar exceção`() = runTest {
        whenever(viewModel.itemRepository.getListaFiltro()).thenThrow(RuntimeException("Erro"))

        val result = viewModel.getListaFiltro()

        assertTrue(result.isFailure)
    }


    //Teste de nome usuarios
    @Test
    fun `getNomeUsuario deve retornar Result success com nome`() = runTest {
        val nomeEsperado = "João Silva"
        whenever(viewModel.userRepository.getNomeUserName()).thenReturn(nomeEsperado)

        val result = viewModel.getNomeUsuario()

        assertTrue(result.isSuccess)
        assertEquals(nomeEsperado, result.getOrNull())
    }

    @Test
    fun `getNomeUsuario deve retornar Result failure quando houver excecao`() = runTest {
        val excecao = RuntimeException("Erro ao buscar nome")
        whenever(viewModel.userRepository.getNomeUserName()).thenThrow(excecao)

        val result = viewModel.getNomeUsuario()

        assertTrue(result.isFailure)
        assertEquals(excecao, result.exceptionOrNull())
    }

    @Test
    fun `getNomeUsuario deve retornar Result success com nome vazio`() = runTest {
        whenever(viewModel.userRepository.getNomeUserName()).thenReturn("")

        val result = viewModel.getNomeUsuario()

        assertTrue(result.isSuccess)
        assertEquals("", result.getOrNull())
    }

    @Test
    fun `getNomeUsuario deve falhar com NullPointerException`() = runTest {
        val exception = NullPointerException("Nome nulo")
        whenever(viewModel.userRepository.getNomeUserName()).thenThrow(exception)

        val result = viewModel.getNomeUsuario()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is NullPointerException)
        assertEquals("Nome nulo", result.exceptionOrNull()?.message)
    }


    //teste get lista item
    @Test
    fun `getListaItem deve retornar Result success com lista de itens completa`() = runTest {
        val itensEsperados = listOf(
            Item(
                idItem = 1L,
                nome = "Tênis Esportivo",
                preco = 199.99,
                tipoItem = TipoItemEnum.TENIS,
                nomeImagem = 123, // simule um drawable ID
                description = "Tênis leve e confortável"
            ),
            Item(
                idItem = 2L,
                nome = "Bota Adventure",
                preco = 299.90,
                tipoItem = TipoItemEnum.BOTAS,
                nomeImagem = 124,
                description = "Bota ideal para trilhas"
            )
        )
        whenever(viewModel.itemRepository.getListaItem()).thenReturn(itensEsperados)

        val result = viewModel.getListaItem()

        assertTrue(result.isSuccess)
        assertEquals(itensEsperados, result.getOrNull())
    }

    @Test
    fun `getListaItem deve retornar Result success com lista vazia`() = runTest {
        whenever(viewModel.itemRepository.getListaItem()).thenReturn(emptyList())

        val result = viewModel.getListaItem()

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }

    @Test
    fun `getListaItem deve retornar Result failure ao ocorrer excecao generica`() = runTest {
        val excecao = RuntimeException("Erro ao carregar itens")
        whenever(viewModel.itemRepository.getListaItem()).thenThrow(excecao)

        val result = viewModel.getListaItem()

        assertTrue(result.isFailure)
        assertEquals(excecao, result.exceptionOrNull())
    }

    @Test
    fun `getListaItem deve retornar Result failure com IOException`() = runTest {
        val excecao = IOException("Falha de conexão")
        whenever(viewModel.itemRepository.getListaItem()).thenThrow(excecao)

        val result = viewModel.getListaItem()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IOException)
        assertEquals("Falha de conexão", result.exceptionOrNull()?.message)
    }


    //teste de filtar por texto
    @Test
    fun `getListaFiltradaQuery deve retornar itens que correspondem ao tipo e nome`() {
        val listaOriginal = listOf(
            Item(1, "Tênis Azul", 100.0, TipoItemEnum.TENIS, 123, "Esportivo"),
            Item(2, "Bota Preta", 200.0, TipoItemEnum.BOTAS, 124, "Adventure")
        )

        val result = viewModel.getListaFiltradaQuery(listaOriginal, "Tênis", TipoItemEnum.TENIS)

        assertTrue(result.isSuccess)
        val listaFiltrada = result.getOrNull()
        assertEquals(1, listaFiltrada?.size)
        assertEquals("Tênis Azul", listaFiltrada?.first()?.nome)
    }

    @Test
    fun `getListaFiltradaQuery deve retornar todos os itens quando tipo for TODOS e busca vazia`() {
        val listaOriginal = listOf(
            Item(1, "Sapatênis Branco", 150.0, TipoItemEnum.SAPATENIS, 123, "Casual"),
            Item(2, "Chuteira Vermelha", 180.0, TipoItemEnum.CHUTEIRA, 124, "Futebol")
        )

        val result = viewModel.getListaFiltradaQuery(listaOriginal, "", TipoItemEnum.TODOS)

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
    }

    @Test
    fun `getListaFiltradaQuery deve retornar lista vazia quando nenhum item corresponder`() {
        val listaOriginal = listOf(
            Item(1, "Tênis Azul", 100.0, TipoItemEnum.TENIS, 123, "Esportivo")
        )

        val result = viewModel.getListaFiltradaQuery(listaOriginal, "bota", TipoItemEnum.BOTAS)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }

}