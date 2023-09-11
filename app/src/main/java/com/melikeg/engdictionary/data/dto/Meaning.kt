package com.melikeg.engdictionary.data.dto

import java.io.Serializable

data class Meaning(
    val antonyms: List<String>,
    val definitions: List<Definition>,
    val partOfSpeech: String,
    val synonyms: List<String>
) : Serializable