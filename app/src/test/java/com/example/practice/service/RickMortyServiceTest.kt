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

class RickMortyServiceTest
{
    lateinit var mockServer : MockServer

    lateinit var rickMortyService: RickMortyService

    @Before
    fun setUp() = runTest{
        mockServer = MockServer()
        rickMortyService = RickMortyService(mockServer.url() )
    }

    @Test
    fun `when getCharacters call success, then return list of Characters`() = runTest {
        val expectedCharactersData = CharacterQuery.Data{
            characters = buildCharacters {
            results = listOf(
                buildCharacter {
                    name = "mom"
                    image = "wave.png"
                }
            )
        }}

        val expectedResponse = RickMortyQLResponse.Success(expectedCharactersData.characters?.results!!)

        val mockResponseBuilder = MockResponse.Builder()
        mockResponseBuilder.body("{\"data\":${expectedCharactersData.toJson()}}")
        mockResponseBuilder.statusCode(200)

        mockServer.enqueue(mockResponseBuilder.build())

        val actualResponse = rickMortyService.getCharacters("")

        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `when getCharacters call error, then return error`() = runTest {
        val expectedResponse = RickMortyQLResponse.Error("Unknown error occurred")

        val mockResponseBuilder = MockResponse.Builder()
        mockResponseBuilder.body("")
        mockResponseBuilder.statusCode(404)

        mockServer.enqueue(mockResponseBuilder.build())

        val actualResponse = rickMortyService.getCharacters("")

        assertEquals(expectedResponse, actualResponse)
    }

    @After
    fun tearDown() {
        mockServer.close()
    }
}