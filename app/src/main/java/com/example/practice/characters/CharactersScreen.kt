package com.example.practice.characters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    characterData: LazyPagingItems<Character>,
    onEvent: (CharactersUserEvent) -> Unit,
    padding: PaddingValues,
    sheetModalVisibility: Pair<Boolean, (Boolean)-> Unit> = Pair(false) {},
    filterTypes : Map<String, CharacterFilterType>,
    onFilterChange : (CharacterFilterType) -> Unit = {}
) {
    CharacterList(characterData, padding, onEvent)
//    when (characterData) {
//        is CharacterData.Success -> {
//            CharacterList(characterData.characters, padding, onEvent)
//
//            if (sheetModalVisibility.first) {
//                ModalBottomSheet(
//                    onDismissRequest = {
//                        sheetModalVisibility.second(false)
//                    }
//                ) {
//                    CharacterFilterForm(filterTypes, onFilterChange)
//                }
//            }
//        }
//
//        is CharacterData.Error -> Box(
//            Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) { Text(text = characterData.message) }
//
//        is CharacterData.Loading -> Box(
//            Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) { CircularProgressIndicator() }

}

@Composable
fun CharacterList(characters: LazyPagingItems<Character>, padding: PaddingValues, onEvent: (CharactersUserEvent) -> Unit) {
    LazyVerticalGrid(modifier = Modifier.padding(padding).padding(10.dp), columns = GridCells.Fixed(3)) {
        items(characters.itemCount) { index ->
            CharacterCard(characters[index] ?: Character(), onEvent)
        }
    }
}