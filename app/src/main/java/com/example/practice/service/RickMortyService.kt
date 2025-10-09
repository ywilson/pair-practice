package com.example.practice.service

import com.apollographql.apollo.ApolloClient
import com.example.practice.CharacterQuery
import javax.inject.Singleton


@Singleton
class RickMortyService(private val serverUrl: String = "https://rickandmortyapi.com/graphql") {
    private val apolloClient = ApolloClient.Builder().serverUrl(serverUrl).build()

    suspend fun getCharacters(nameSearch: String) : List<CharacterQuery.Result?> {

        val response = apolloClient.query(CharacterQuery(nameSearch)).execute()

        response.data?.also { data ->
            data.characters?.also {characters ->
                characters.results?.also {
                    return it
                }
            }
        }

        return emptyList()
    }
}