package com.melikeg.engdictionary.domain.usecase

import com.melikeg.engdictionary.common.NetworkResponse
import com.melikeg.engdictionary.domain.model.WordItem
import com.melikeg.engdictionary.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWordUseCaseImpl @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
): GetWordUseCase {
    override fun invoke(word: String): Flow<NetworkResponse<WordItem>> = flow {
        emit(NetworkResponse.Loading)
        emit(dictionaryRepository.getWord(word))

    }
}