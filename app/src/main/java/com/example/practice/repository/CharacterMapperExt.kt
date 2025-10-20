package com.example.practice.repository

import com.example.practice.CharacterQuery
import com.example.practice.characters.Character

fun CharacterQuery.Result.toCharacter() = Character(name ?: "", image ?: "", status ?: "", gender ?: "")


