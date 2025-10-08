package com.example.practice.service

import com.apollographql.apollo.api.toJson
import com.apollographql.mockserver.MockResponse
import com.apollographql.mockserver.MockServer
import com.apollographql.mockserver.enqueueString
import com.example.practice.CharacterQuery
import com.example.practice.type.buildCharacter
import com.example.practice.type.buildCharacters
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RickMortyServiceTest
{
    @Test
    fun `when getCharacters call, then return list of Characters`() = runTest {
        val mockServer = MockServer()
        MockResponse.Builder()

        val query = CharacterQuery.Data{
            characters = buildCharacters {
            results = listOf(
                buildCharacter {
                    name = "mom"
                    image = "wave.png"
                }
            )
        }}
        mockServer.enqueueString(query.toJson())

        val rickMortyService = RickMortyService(mockServer.url() )

        val actualResponse = rickMortyService.getCharacters("mom")

    }
}