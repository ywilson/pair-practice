package com.example.practice.repository

import com.example.practice.characters.Character
import com.example.practice.characters.CharacterData
import com.example.practice.service.RickMortyQLResponse
import com.example.practice.service.RickMortyService
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface CharacterRepository {
    suspend fun getCharacters(nameFilter: String): CharacterData
}


class CharacterRepositoryImpl @Inject constructor(private val rickMortyService: RickMortyService) :
    CharacterRepository {
    override suspend fun getCharacters(nameFilter: String): CharacterData {
        return when (val response = rickMortyService.getCharacters("")) {
            is RickMortyQLResponse.Success -> CharacterData.Success(response.characters.map {
                it?.toCharacter() ?: Character("", "")
            })

            is RickMortyQLResponse.Error -> {
                CharacterData.Error(response.message)
            }
        }
    }
}