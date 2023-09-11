package com.melikeg.engdictionary.data.api

import com.melikeg.engdictionary.data.dto.WordList
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApiService {

    @GET("api/v2/entries/en/{word}")
    suspend fun getWord(@Path("word") word: String): WordList

}