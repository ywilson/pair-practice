package com.example.practice.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.practice.R

@Composable
fun CharacterDetailsCard(
    character: Character,
    padding : PaddingValues
    ) {
    Card ( modifier = Modifier.padding(padding).padding(horizontal = 10.dp).fillMaxSize()) {
        Column (modifier = Modifier.padding(horizontal = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(75.dp)),
                placeholder = painterResource(R.drawable.baseline_broken_image_24),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .build(),
                contentDescription = "characterImage"
            )
            Text("Species: ${character.species}")
            Text("Type: ${if(character.type!="") character.type else "N/A" }")
            Text("Gender: ${character.gender}")
            Text("Origin Name: ${character.origin.name}")
            Text("Origin Dimension: ${character.origin.dimension}")
            Text("Current Location Name: ${character.location.name}")
            Text("Current Location Dimension: ${character.location.dimension}")
            Text("Episode List")
            LazyRow {
                character.episodes.forEach { episode ->
                    item {
                        EpisodeCard(episode)
                    }
                }
            }
        }
    }
}

@Composable
fun EpisodeCard(episode: Episode){
    Card(modifier = Modifier.padding(horizontal = 5.dp).height(100.dp), colors = CardDefaults.cardColors().copy(containerColor = Color.Gray)) {
        Column(modifier = Modifier.padding(horizontal = 10.dp).align(Alignment.CenterHorizontally).fillMaxHeight(), verticalArrangement = Arrangement.Center) {
            Text("Episode: ${episode.episode}")
            Text("Name: ${episode.name}")
        }
    }
}

@Preview
@Composable
fun CharacterDetailsCardPreview(){
    CharacterDetailsCard(Character("suuuupeerrrrr longggggg azzzzzzz nammmmmeeeeees", "http://dog.img", "dead"), PaddingValues(5.dp))
}