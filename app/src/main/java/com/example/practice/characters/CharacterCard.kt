package com.example.practice.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.practice.R

@Composable
fun CharacterCard(character: Character) {
    Card {
        Column {
            Text(text = character.name)
            AsyncImage(
                placeholder = painterResource(R.drawable.baseline_broken_image_24),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .build(),
                contentDescription = "characterImage"
            )
            Text(text = character.status)
        }
    }
}