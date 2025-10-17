package com.example.practice

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.example.practice.characters.Character
import com.example.practice.characters.CharacterCard
import com.example.practice.characters.CharacterData
import com.example.practice.characters.CharactersScreen
import com.example.practice.characters.CharactersUserEvent
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

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

    @Test
    fun whenGivenCharacter_andCardClicked_thenPassCharacterThroughEvent()
    {
        val onEvent : (CharactersUserEvent) -> Unit = mock()

        val testCharacterData = CharacterData.Success(
            listOf(
                Character("mom", "wave.png", "Alive"),
                Character("dad", "high5.png", "Dead")
            ))

        composeTestRule.setContent {
            CharactersScreen(
                testCharacterData, onEvent,
                padding = PaddingValues(5.dp)
            )
        }

        composeTestRule.onNodeWithText(testCharacterData.characters[0].name).performClick()

        verify(onEvent, times(1)).invoke(CharactersUserEvent.ButtonClick(testCharacterData.characters[0]))
    }
}