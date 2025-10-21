package com.example.practice.characters

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.practice.R

@Composable
fun CharacterCard(
    character: Character,
    onEvent: (CharactersUserEvent) -> Unit
    ) {
    Card (modifier = Modifier.width(130.dp).fillMaxHeight().padding(5.dp), onClick = {onEvent(CharactersUserEvent.ButtonClick(character))}) {
        Column (modifier = Modifier.padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally){
            Text(modifier = Modifier.widthIn(max = 120.dp).height(22.dp), text = character.name, overflow = TextOverflow.Ellipsis)
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

@Preview
@Composable
fun CharacterCardPreview(){
    CharacterCard(Character("suuuupeerrrrr longggggg azzzzzzz nammmmmeeeeees", "http://dog.img", "dead"), {})
}