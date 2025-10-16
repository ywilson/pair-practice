package com.example.practice

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import com.example.practice.characters.Character
import com.example.practice.characters.CharacterCard
import org.junit.Rule
import org.junit.Test

class CharacterCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenGivenCharacter_thenDisplayCharacterCard() {
        val testCharacter = Character("test name", "", "Alive")

        composeTestRule.setContent {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CharacterCard(testCharacter)
                }
        }

        composeTestRule
            .onNodeWithText(testCharacter.name)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("characterImage")
            .assertIsDisplayed()
    }
}