package com.melikeg.engdictionary.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melikeg.engdictionary.R
import com.melikeg.engdictionary.common.NetworkResponse
import com.melikeg.engdictionary.domain.mapper.WordMapper
import com.melikeg.engdictionary.domain.model.WordItem
import com.melikeg.engdictionary.domain.usecase.GetWordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWordUseCase: GetWordUseCase,
    private val wordMapper: WordMapper<WordItem, HomeUiData>
): ViewModel() {

    private var _wordDataUiState = MutableLiveData<HomeUiState<HomeUiData>>()
    val wordDataUiState: LiveData<HomeUiState<HomeUiData>> get() = _wordDataUiState


    fun getWord(word: String){
        viewModelScope.launch {
            getWordUseCase.invoke(word)
                .collect{ response ->
                    when(response){
                        is NetworkResponse.Loading -> _wordDataUiState.postValue(HomeUiState.Loading)
                        is NetworkResponse.Error -> {
                            _wordDataUiState.postValue(HomeUiState.Error(R.string.unknown_error_occured))
                            Log.d("errorr", response.exception.toString())


                        }
                        is NetworkResponse.Success -> {
                            _wordDataUiState.postValue(HomeUiState.Success(wordMapper.map(response.result)))
                            Log.d("resultt", response.result.word)
                        }
                    }

                }
        }
    }



}