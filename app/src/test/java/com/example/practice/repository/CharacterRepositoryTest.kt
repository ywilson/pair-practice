package com.example.practice.repository

import com.example.practice.CharacterQuery
import com.example.practice.characters.Character
import com.example.practice.characters.CharacterData
import com.example.practice.service.RickMortyQLResponse
import com.example.practice.service.RickMortyService
import com.example.practice.type.buildCharacter
import com.example.practice.type.buildCharacters
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class CharacterRepositoryTest {
    @Mock
    lateinit var rickMortyService: RickMortyService

    @InjectMocks
    lateinit var characterRepositoryImpl: CharacterRepositoryImpl

    @Test
    fun `when getCharactersCalled success, return characters from R&Mapi`() = runTest {
        val testCharactersData = CharacterQuery.Data {
            characters = buildCharacters {
                results = listOf(
                    buildCharacter {
                        name = "mom"
                        image = "wave.png"
                        status = "Alive"
                    },
                    buildCharacter {
                        name = "test"
                        image = "wave3.png"
                        status = "Alive"
                    },
                    buildCharacter {
                        name = "dad"
                        image = "clap.png"
                        status = "Dead"
                    }
                )
            }
        }

        val expectedCharactersData = CharacterData.Success(listOf(
            Character(
                name = "mom",
                image = "wave.png",
                status = "Alive"
            ),
            Character(
                name = "test",
                image = "wave3.png",
                status = "Alive"
            ),
            Character(
                name = "dad",
                image = "clap.png",
                status = "Dead"
            )
        ))

        whenever(rickMortyService.getCharacters("")).thenReturn(
            RickMortyQLResponse.Success(testCharactersData.characters?.results!!)
        )

        val actualCharacters = characterRepositoryImpl.getCharacters("")

        verify(rickMortyService).getCharacters("")
        assertEquals(expectedCharactersData, actualCharacters)
    }

    @Test
    fun `when getCharactersCalled error, return error`() = runTest {
        val expectedError = CharacterData.Error("Unknown error occurred")

        whenever(rickMortyService.getCharacters("")).thenReturn(
            RickMortyQLResponse.Error("Unknown error occurred")
        )

        val actualResponse = characterRepositoryImpl.getCharacters("")

        verify(rickMortyService).getCharacters("")
        assertEquals(expectedError, actualResponse)
    }
}