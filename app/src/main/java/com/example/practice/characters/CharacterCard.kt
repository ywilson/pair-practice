package com.example.practice.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import coil.compose.AsyncImage

@Composable
fun CharacterCard(character: Character) {
    Card {
        Column {
            Text(text = character.name)
            AsyncImage(model = character.image, contentDescription = "characterImage")
        }
    }
}