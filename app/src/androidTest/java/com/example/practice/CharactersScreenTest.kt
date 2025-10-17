package com.example.practice

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import com.example.practice.characters.Character
import com.example.practice.characters.CharacterData
import com.example.practice.characters.CharactersScreen
import org.junit.Rule
import org.junit.Test

class CharactersScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenGivenSuccessCharacterData_thenDisplayCharacterList()
    {
        val testCharacterData = CharacterData.Success(
            listOf(
                Character("mom", "wave.png", "Alive"),
                Character("dad", "high5.png", "Dead")
            ))


        composeTestRule.setContent {
            CharactersScreen(
                testCharacterData, {},
                padding = PaddingValues(5.dp)
            )
        }

        testCharacterData.characters.forEach {
            composeTestRule.onNodeWithText(it.name)
                .assertIsDisplayed()
        }
    }

    @Test
    fun whenGivenErrorCharacterData_thenDisplayErrorMessage()
    {
        val errorCharacterData = CharacterData.Error("Unknown error occurred")

        composeTestRule.setContent {
            CharactersScreen(errorCharacterData, {},  padding = PaddingValues(5.dp))
        }

        composeTestRule
            .onNodeWithText("Unknown error occurred")
            .assertIsDisplayed()
    }
}