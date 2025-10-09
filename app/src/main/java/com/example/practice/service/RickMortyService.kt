package com.example.practice.service

import com.apollographql.apollo.ApolloClient
import com.example.practice.CharacterQuery
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RickMortyService  {

    var apolloClient : ApolloClient

    @Inject constructor(){
        apolloClient = ApolloClient.Builder().serverUrl(SERVER_URL).build()
    }

    constructor(serverUrl : String) {
        apolloClient = ApolloClient.Builder().serverUrl(serverUrl).build()
    }

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

    companion object
    {
        const val SERVER_URL = "https://rickandmortyapi.com/graphql"
    }
}