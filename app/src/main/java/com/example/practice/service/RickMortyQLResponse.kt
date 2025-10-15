package com.example.practice.service

import com.example.practice.CharacterQuery

sealed interface RickMortyQLResponse {
    data class Success(val characters : List<CharacterQuery.Result?>) : RickMortyQLResponse
    data class Error(val message : String) : RickMortyQLResponse
}