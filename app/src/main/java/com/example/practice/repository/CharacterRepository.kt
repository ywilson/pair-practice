package com.example.practice.repository

import com.example.practice.characters.Character
import com.example.practice.characters.CharacterData
import com.example.practice.service.RickMortyQLResponse
import com.example.practice.service.RickMortyService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface CharacterRepository {
    suspend fun getCharacters(nameFilter: String = "", genderFilter: String = "", statusFilter: String = "", currentPage : Int = 1): CharacterData
}


class CharacterRepositoryImpl @Inject constructor(private val rickMortyService: RickMortyService) :
    CharacterRepository {
    override suspend fun getCharacters(nameFilter: String, genderFilter: String, statusFilter: String, currentPage : Int): CharacterData {
        return when (val response = rickMortyService.getCharacters(nameFilter, genderFilter, statusFilter, currentPage)) {
            is RickMortyQLResponse.Success -> CharacterData.Success(response.characters.map {
                it?.toCharacter() ?: Character()
            })

            is RickMortyQLResponse.Error -> {
                CharacterData.Error(response.message)
            }
        }
    }
}