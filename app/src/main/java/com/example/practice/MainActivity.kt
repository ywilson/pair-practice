package com.example.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practice.characters.Character
import com.example.practice.characters.CharactersScreen
import com.example.practice.characters.CharactersUserEvent
import com.example.practice.characters.CharactersViewModel
import com.example.practice.ui.theme.PracticeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val charactersViewModel: CharactersViewModel by viewModels()

    @Serializable
    object CharacterList

    @Serializable
    object CharacterDetails

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val characterData = charactersViewModel.characterFlow.collectAsState()
            val currentCharacter = charactersViewModel.currentCharacterFlow.collectAsState()
            val navController = rememberNavController()
            var sheetModelVisibilityState by remember { mutableStateOf(false) }

            PracticeTheme(darkTheme = true) {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(title = {
                        Text(text = currentCharacter.value.name)
                    }, navigationIcon = {
                        if (currentCharacter.value.name != "") {
                            IconButton(onClick = {
                                navController.popBackStack()
                                charactersViewModel.handleUserEvent(
                                    CharactersUserEvent.ButtonClick(
                                        Character(
                                            "",
                                            "",
                                            ""
                                        )
                                    )
                                )
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = "back button",
                                    tint = Color.White
                                )
                            }
                        }}, actions = {
                            IconButton(onClick = {sheetModelVisibilityState = !sheetModelVisibilityState}) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "filter button",
                                    tint = Color.White
                                )
                            }
                    })
                }
                ) { padding ->
                    NavHost(navController = navController, startDestination = CharacterList)
                    {
                        composable<CharacterList> {
                            CharactersScreen(
                                characterData.value,
                                onEvent = {
                                    charactersViewModel.handleUserEvent(it)
                                    navController.navigate(CharacterDetails)
                                },
                                padding,
                                Pair(sheetModelVisibilityState) { sheetModelVisibilityState = it }
                            )
                        }
                        composable<CharacterDetails> { Text("hello") }
                    }
                }
            }
        }

        charactersViewModel.refreshCharacters()
    }
}