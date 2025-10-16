package com.example.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.example.practice.characters.CharactersScreen
import com.example.practice.characters.CharactersViewModel
import com.example.practice.ui.theme.PracticeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val charactersViewModel: CharactersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val characterData = charactersViewModel.characterFlow.collectAsState()

            PracticeTheme(darkTheme = true) {
                CharactersScreen(characterData.value, onEvent = {charactersViewModel.handleUserEvent(it)})
            }
        }

        charactersViewModel.refreshCharacters()
    }
}