package com.melikeg.engdictionary.presentation.home

import com.melikeg.engdictionary.data.dto.Definition
import com.melikeg.engdictionary.data.dto.Meaning

data class HomeUiData(
    val word: String,
    val meanings: List<Meaning>,
    val partOfSpeech: List<String>,
    val phoneticText: List<String>,
    val definitions: List<List<Definition>>,
    val audioUrl: List<String>,
    val examples: List<List<String>>,
    val antonyms: List<List<String>>,
    val synonyms: List<List<String>>,
    val sourceUrl: String
    )