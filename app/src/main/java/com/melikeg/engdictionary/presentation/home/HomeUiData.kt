package com.melikeg.engdictionary.presentation.home

data class HomeUiData(
    val word: String,
    val partOfSpeech: List<String>,
    val phoneticText: String,
    val definitions: List<List<String>>,
    val examples: List<List<String>>,
    val antonyms: List<List<String>>,
    val synonyms: List<List<String>>,
    val sourceUrl: String
    )