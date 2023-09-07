package com.melikeg.engdictionary.data.repository

import com.melikeg.engdictionary.common.NetworkResponse
import com.melikeg.engdictionary.data.source.remote.RemoteDataSource
import com.melikeg.engdictionary.domain.model.WordItem
import com.melikeg.engdictionary.domain.repository.DictionaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject


class DictionaryRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): DictionaryRepository {
    override suspend fun getWord(word: String): NetworkResponse<WordItem> =
        withContext(Dispatchers.IO){
            try {
                remoteDataSource.getWord(word)

            }catch (e: Exception){
                NetworkResponse.Error(e)
            }
        }


}