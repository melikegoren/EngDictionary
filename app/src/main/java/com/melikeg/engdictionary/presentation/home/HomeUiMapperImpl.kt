package com.melikeg.engdictionary.presentation.home

import com.melikeg.engdictionary.domain.mapper.WordMapper
import com.melikeg.engdictionary.domain.model.WordItem
import javax.inject.Inject

class HomeUiMapperImpl @Inject constructor(): WordMapper<WordItem, HomeUiData> {
    override fun map(input: WordItem?): HomeUiData {


        return input.let {
            HomeUiData(
                word = it?.word.toString(),
                meanings = it?.meanings!!,
                partOfSpeech = it?.meanings?.map { it.partOfSpeech }!!,
                phoneticText = it.phonetics.map { it.text },
                definitions = it.meanings.map { it.definitions },
                audioUrl = it.phonetics.map { it.audio },
                examples = it.meanings.map { it.definitions.map { it.example } },
                antonyms = it.meanings.map { it.antonyms },
                synonyms = it.meanings.map { it.synonyms },
                sourceUrl = it.sourceUrl,
            )
        }
    }
}