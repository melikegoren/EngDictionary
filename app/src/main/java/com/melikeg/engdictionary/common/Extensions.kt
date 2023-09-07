package com.melikeg.engdictionary.common

import com.melikeg.engdictionary.data.dto.WordResponseItem
import com.melikeg.engdictionary.domain.model.WordItem

fun WordResponseItem.toWordItem(): WordItem{
    /*val meanings = meanings.map{
        Meaning(listOf(it.partOfSpeech),  it.definitions, it.antonyms.toString(), it.synonyms)
    }*/

    return WordItem(
        word = word,
        meanings = meanings,
        phonetic = phonetics[0],
        sourceUrl = sourceUrls[0]
    )

}