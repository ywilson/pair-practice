package com.example.practice.repository

import com.example.practice.CharacterQuery
import com.example.practice.characters.Character
import com.example.practice.characters.Episode
import com.example.practice.characters.Location

fun CharacterQuery.Result.toCharacter() = Character(
    name ?: "",
    image ?: "",
    status ?: "",
    gender ?: "",
    species ?: "",
    type ?: "",
    origin?.toLocation() ?: Location(),
    location?.toLocation() ?: Location(),
    episode.toEpisodeList()
)

fun CharacterQuery.Origin.toLocation() = Location(
    name ?: "",
    type ?: "",
    dimension ?: ""
)

fun CharacterQuery.Location.toLocation() = Location(
    name ?: "",
    type ?: "",
    dimension ?: ""
)

fun List<CharacterQuery.Episode?>.toEpisodeList() : List<Episode> {
    return mapNotNull { episode -> episode?.toEpisode() }
}

fun CharacterQuery.Episode.toEpisode() = Episode(
    name ?: "",
    episode ?: ""
)


