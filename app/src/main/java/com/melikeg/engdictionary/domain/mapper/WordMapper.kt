package com.melikeg.engdictionary.domain.mapper

interface WordMapper<I, O> {
    fun map(input: I?): O
}