package com.example.practice.service

import com.apollographql.apollo.api.toJson
import com.apollographql.mockserver.MockResponse
import com.apollographql.mockserver.MockServer
import com.example.practice.CharacterQuery
import com.example.practice.type.buildCharacter
import com.example.practice.type.buildCharacters
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RickMortyServiceTest
{
    lateinit var mockServer : MockServer
    @Before
    fun setUp() {
        mockServer = MockServer()
    }

    @Test
    fun `when getCharacters call, then return list of Characters`() = runTest {
        val expectedCharactersData = CharacterQuery.Data{
            characters = buildCharacters {
            results = listOf(
                buildCharacter {
                    name = "mom"
                    image = "wave.png"
                }
            )
        }}

        val mockResponseBuilder = MockResponse.Builder()
        mockResponseBuilder.body("{\"data\":${expectedCharactersData.toJson()}}")
        mockResponseBuilder.statusCode(200)

        mockServer.enqueue(mockResponseBuilder.build())

        val rickMortyService = RickMortyService(mockServer.url() )

        val actualResponse = rickMortyService.getCharacters("mom")

        assertEquals(expectedCharactersData.characters?.results, actualResponse)
    }

    @After
    fun tearDown() {
        mockServer.close()
    }
}