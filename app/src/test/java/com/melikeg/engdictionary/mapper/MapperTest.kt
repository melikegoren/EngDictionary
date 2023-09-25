package com.melikeg.engdictionary.mapper

import com.melikeg.engdictionary.domain.mapper.WordMapper
import com.melikeg.engdictionary.domain.model.WordItem
import com.melikeg.engdictionary.presentation.home.HomeUiData
import com.melikeg.engdictionary.presentation.home.HomeUiMapperImpl
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Assert.assertNotNull
import org.junit.Test

class WordMapperTest {
    private val wordMapper: WordMapper<WordItem, HomeUiData> = HomeUiMapperImpl()

    @Test
    fun testMappingNullInput() {
        val result = wordMapper.map(null)
        assertNull(result)

    }

    @Test
    fun testMappingValidInput() {
        val input = WordItem("", emptyList(), emptyList(), "")
        val result = wordMapper.map(input)
        assertNotNull(result)
        assertEquals(HomeUiData("", emptyList(), emptyList(), emptyList(), emptyList(),
            emptyList(), emptyList(), emptyList(), emptyList(),""
        ), result)
    }


}

