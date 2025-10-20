package com.example.practice.characters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    characterData: CharacterData,
    onEvent: (CharactersUserEvent) -> Unit,
    padding: PaddingValues,
    sheetModalVisibility: Pair<Boolean, (Boolean)-> Unit> = Pair(false) {}
) {

    when (characterData) {
        is CharacterData.Success -> {
            CharacterList(characterData.characters, padding, onEvent)

            if (sheetModalVisibility.first) {
                ModalBottomSheet(
                    onDismissRequest = {
                        sheetModalVisibility.second(false)
                    }
                ) {
                    Text("your moma")
                }
            }
        }

        is CharacterData.Error -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { Text(text = characterData.message) }

        is CharacterData.Loading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }
    }
}

@Composable
fun CharacterList(characters: List<Character>, padding: PaddingValues, onEvent: (CharactersUserEvent) -> Unit) {
    LazyColumn(modifier = Modifier.padding(padding)) {
        items(characters.chunked(3)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEach {
                    CharacterCard(it, onEvent)
                }
            }
        }
    }
}