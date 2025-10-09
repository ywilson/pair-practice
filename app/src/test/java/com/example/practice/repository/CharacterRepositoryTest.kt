package com.example.practice.repository

import com.example.practice.CharacterQuery
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
        val expectedCharactersData = CharacterQuery.Data {
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

        whenever(rickMortyService.getCharacters("")).thenReturn(
            expectedCharactersData.characters?.results)

        val actualCharacters = characterRepositoryImpl.getCharacters("")

        verify(rickMortyService).getCharacters("")
        assertEquals(expectedCharactersData.characters?.results, actualCharacters)
    }
}