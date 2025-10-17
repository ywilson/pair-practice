package com.example.practice

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.practice.characters.Character
import com.example.practice.characters.CharacterCard
import com.example.practice.characters.CharactersUserEvent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Mock
    private lateinit var onEvent : (CharactersUserEvent) -> Unit
    private val testCharacter = Character("test name", "", "Alive")

    @Test
    fun whenGivenCharacter_thenDisplayCharacterCard() {
        composeTestRule.setContent {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CharacterCard(testCharacter) {}
                }
        }

        composeTestRule
            .onNodeWithText(testCharacter.name)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("characterImage")
            .assertIsDisplayed()
    }

    @Test
    fun whenGivenCharacter_andCardClicked_thenPassCharacterThroughEvent()
    {
        composeTestRule.setContent {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CharacterCard(testCharacter, onEvent)
            }
        }

        composeTestRule.onNodeWithText(testCharacter.name).performClick()

        verify(onEvent, times(1)).invoke(CharactersUserEvent.ButtonClick(testCharacter))
    }
}