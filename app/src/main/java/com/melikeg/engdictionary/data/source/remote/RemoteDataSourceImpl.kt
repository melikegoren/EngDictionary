package com.melikeg.engdictionary.data.source.remote

import com.melikeg.engdictionary.common.NetworkResponse
import com.melikeg.engdictionary.common.toWordItem
import com.melikeg.engdictionary.data.api.DictionaryApiService
import com.melikeg.engdictionary.domain.model.WordItem
import java.lang.Exception
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val dictionaryApi: DictionaryApiService
): RemoteDataSource {
    override suspend fun getWord(word: String): NetworkResponse<WordItem> =
        try {
            val response = dictionaryApi.getWord(word)[0].toWordItem()
            NetworkResponse.Success(response)
        }catch (e: Exception){
            NetworkResponse.Error(e)

        }
}