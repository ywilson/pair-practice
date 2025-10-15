package com.example.practice

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.practice.characters.Character
import com.example.practice.characters.CharacterCard
import org.junit.Rule
import org.junit.Test

class CharacterCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenGivenCharacter_thenDisplayCharacterCard()
    {
        val testCharacter = Character("test name", "http://testimage.png")

        composeTestRule.setContent {
            CharacterCard(testCharacter)
        }

        composeTestRule
            .onNodeWithText(testCharacter.name)
            .assertIsDisplayed()

    }
}