package com.melikeg.engdictionary.domain.usecase

import com.melikeg.engdictionary.common.NetworkResponse
import com.melikeg.engdictionary.domain.model.WordItem
import kotlinx.coroutines.flow.Flow

interface GetWordUseCase {

    operator fun invoke(word: String): Flow<NetworkResponse<WordItem>>
}