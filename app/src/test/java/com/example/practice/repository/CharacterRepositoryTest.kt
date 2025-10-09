package com.example.practice.repository

import com.example.practice.CharacterQuery
import com.example.practice.characters.Character
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
    fun `when getCharactersCalled, return characters from R&Mapi`() = runTest {
        val testCharactersData = CharacterQuery.Data {
            characters = buildCharacters {
                results = listOf(
                    buildCharacter {
                        name = "mom"
                        image = "wave.png"
                    },
                    buildCharacter {
                        name = "test"
                        image = "wave3.png"
                    },
                    buildCharacter {
                        name = "dad"
                        image = "clap.png"
                    }
                )
            }
        }

        val expectedCharacters = listOf(
            Character(
                name = "mom",
                image = "wave.png"
            ),
            Character(
                name = "test",
                image = "wave3.png"
            ),
            Character(
                name = "dad",
                image = "clap.png"
            )
        )

        whenever(rickMortyService.getCharacters("")).thenReturn(
            testCharactersData.characters?.results
        )

        val actualCharacters = characterRepositoryImpl.getCharacters("")

        verify(rickMortyService).getCharacters("")
        assertEquals(expectedCharacters, actualCharacters)
    }
}