package com.example.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.practice.characters.*
import com.example.practice.ui.theme.PracticeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
            val filterTypes = charactersViewModel.filterFlow.collectAsState()
            var searchText by remember { mutableStateOf("") }
            val searchBoxInteractionSource = remember { MutableInteractionSource() }
            val charactersPaged = charactersViewModel.characterItems.collectAsLazyPagingItems()

            PracticeTheme(darkTheme = true) {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(title = {
                        if (currentCharacter.value.name == "")
                            BasicTextField(
                                value = searchText,
                                onValueChange = {
                                    searchText = it
                                    charactersViewModel.filterCharactersByFilterType(CharacterFilterType.Name(it))
                                },
                                modifier = Modifier.fillMaxWidth().border(
                                    width = 2.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(8.dp)
                                ).padding(5.dp),
                                textStyle = TextStyle.Default.copy(color = Color.White),
                                singleLine = true,
                                interactionSource = searchBoxInteractionSource,
                                decorationBox = @Composable { innerTextField ->
                                    TextFieldDefaults.DecorationBox(
                                        value = searchText,
                                        innerTextField = innerTextField,
                                        enabled = true,
                                        singleLine = true,
                                        visualTransformation = VisualTransformation.None,
                                        interactionSource = searchBoxInteractionSource,
                                        placeholder = {
                                            Text("Search Character Name")
                                        },
                                        contentPadding = PaddingValues(2.dp)
                                    )
                                },
                                cursorBrush = SolidColor(Color.White)
                            )
                        else {
                            Row (verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = {
                                    navController.popBackStack()
                                    lifecycleScope.launch {
                                        delay(200)
                                        charactersViewModel.handleUserEvent(
                                        CharactersUserEvent.ButtonClick(
                                            Character(
                                                "",
                                                "",
                                                ""
                                            )
                                        )
                                    ) }
                                }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                        contentDescription = "back button",
                                        tint = Color.White
                                    )
                                }
                                Text(text = currentCharacter.value.name)
                            }
                        }
                    }, actions = {
                        IconButton(onClick = { sheetModelVisibilityState = !sheetModelVisibilityState }) {
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
                                charactersPaged,
                                onEvent = {
                                    charactersViewModel.handleUserEvent(it)
                                    navController.navigate(CharacterDetails)
                                },
                                padding,
                                Pair(sheetModelVisibilityState) { sheetModelVisibilityState = it },
                                filterTypes.value,
                                { charactersViewModel.filterCharactersByFilterType(it) }
                            )
                        }
                        composable<CharacterDetails> { CharacterDetailsCard(currentCharacter.value, padding) }
                    }
                }
            }
        }

        charactersViewModel.refreshCharacters()
    }
}