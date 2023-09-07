package com.melikeg.engdictionary.domain.model

import com.melikeg.engdictionary.data.dto.Meaning
import com.melikeg.engdictionary.data.dto.Phonetic

data class WordItem(
    val word: String,
    val meanings: List<Meaning>,
    val phonetic: Phonetic,
    val sourceUrl: String
    )