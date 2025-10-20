package com.example.practice

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.practice.characters.CharacterFilterForm
import com.example.practice.characters.CharacterFilterType
import org.junit.Rule
import org.junit.Test

class CharacterFilterFormTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenGivenFilters_thenDisplayFilters()
    {
        val testFilters = mapOf(
            Pair("Gender", CharacterFilterType.Gender(male = true)),
            Pair("Status",CharacterFilterType.Status(alive = true)))

        composeTestRule.setContent {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CharacterFilterForm(testFilters)
            }
        }

        Thread.sleep(10000)
    }
}