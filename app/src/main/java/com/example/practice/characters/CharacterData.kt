package com.example.practice.characters

sealed interface CharacterData {
    object Loading : CharacterData
    data class Success(val characters : List<Character>) : CharacterData
    data class Error(val message : String) : CharacterData
}