package com.example.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.practice.characters.Character
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
            val characterList = charactersViewModel.characterFlow.collectAsState()
            PracticeTheme(darkTheme = true) {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    CharacterList(characterList.value)
                }
            }
        }

        charactersViewModel.refreshCharacters()
    }
}

@Composable
fun CharacterList(characters: List<Character>) {
    LazyColumn {
        items(characters.size) {
            Text(text = characters[it].name)
        }
    }
}