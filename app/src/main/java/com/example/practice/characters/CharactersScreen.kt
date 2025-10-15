package com.example.practice.characters

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CharactersScreen(characterData : CharacterData) {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    Scaffold(modifier = Modifier.fillMaxSize()) {  _ ->
        when (characterData)
        {
            is CharacterData.Success -> CharacterList(characterData.characters)
            is CharacterData.Error -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {Text(text = characterData.message)}
            is CharacterData.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){CircularProgressIndicator()}
        }
    }
}

@Composable
fun CharacterList(characters: List<Character>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()){
        items(characters.size) {
            CharacterCard(characters[it])
        }
    }
}