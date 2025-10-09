package com.example.practice.repository

import com.example.practice.CharacterQuery
import com.example.practice.service.RickMortyService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface CharacterRepository {
    suspend fun getCharacters(nameFilter: String) : List<CharacterQuery.Result?>
}


class CharacterRepositoryImpl @Inject constructor(private val rickMortyService: RickMortyService) : CharacterRepository {
    override suspend fun getCharacters(nameFilter: String): List<CharacterQuery.Result?> {
        return rickMortyService.getCharacters("")
    }
}