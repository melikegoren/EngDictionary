package com.melikeg.engdictionary.data.source.remote

import com.melikeg.engdictionary.common.NetworkResponse
import com.melikeg.engdictionary.domain.model.WordItem

interface RemoteDataSource {

    suspend fun getWord(word: String): NetworkResponse<WordItem>

}