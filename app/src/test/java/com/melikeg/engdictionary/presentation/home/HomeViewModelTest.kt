@file:OptIn(ExperimentalCoroutinesApi::class)

package com.melikeg.engdictionary.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.melikeg.engdictionary.common.NetworkResponse
import com.melikeg.engdictionary.domain.mapper.WordMapper
import com.melikeg.engdictionary.domain.model.WordItem
import com.melikeg.engdictionary.domain.usecase.GetWordUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    private val getWordUseCase: GetWordUseCase = mockk()
    private val wordMapper: WordMapper<WordItem, HomeUiData> = mockk()

    @Before
    fun setup() {
        viewModel = HomeViewModel(getWordUseCase, wordMapper)
        //MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /*@Test
    fun testGetWord_SuccessState() = runBlockingTest {
        val word = "exampleWord"
        val responseResult = WordItem("")
        val expectedUiData = HomeUiData("")

        coEvery { getWordUseCase.invoke(word) } returns flowOf(NetworkResponse.Success(responseResult))
        coEvery { wordMapper.map(responseResult) } returns expectedUiData

        viewModel.getWord(word)

        assertEquals(HomeUiState.Success(expectedUiData), viewModel.wordDataUiState.value)
    }*/

    @Test
    fun testGetWord_ErrorState() = runBlockingTest {
        val word = "abcdgfghj"
        val errorMessage = "Error message"

        coEvery { getWordUseCase.invoke(word) } returns flowOf(NetworkResponse.Error(Exception(errorMessage)))

        viewModel.getWord(word)

        assertEquals(HomeUiState.Error("java.lang.Exception: "+errorMessage), viewModel.wordDataUiState.value)
    }

    @Test
    fun testGetWord_LoadingState() = runBlockingTest {
        val word = "loadingWord"

        coEvery { getWordUseCase.invoke(word) } returns flowOf(NetworkResponse.Loading)

        viewModel.getWord(word)

        assertEquals(HomeUiState.Loading, viewModel.wordDataUiState.value)
    }
}
