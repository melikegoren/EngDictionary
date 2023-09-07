package com.melikeg.engdictionary.presentation.home

import com.melikeg.engdictionary.domain.mapper.WordMapper
import com.melikeg.engdictionary.domain.model.WordItem
import javax.inject.Inject

class HomeUiMapperImpl @Inject constructor(): WordMapper<WordItem, HomeUiData> {
    override fun map(input: WordItem?): HomeUiData {
        return input.let {
            HomeUiData(
                word = it?.word.toString(),
                partOfSpeech = it?.meanings?.map { it.partOfSpeech }!!,
                phoneticText = it.phonetic.text,
                definitions = it.meanings.map { it.definitions.map { it.definition } },
                examples = it.meanings.map { it.definitions.map { it.example } },
                antonyms = it.meanings.map { it.antonyms },
                synonyms = it.meanings.map { it.synonyms },
                sourceUrl = it.sourceUrl
            )
        }
    }
}