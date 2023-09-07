package com.melikeg.engdictionary.domain.repository

import com.melikeg.engdictionary.common.NetworkResponse
import com.melikeg.engdictionary.domain.model.WordItem

interface DictionaryRepository {
    suspend fun getWord(word: String): NetworkResponse<WordItem>
}