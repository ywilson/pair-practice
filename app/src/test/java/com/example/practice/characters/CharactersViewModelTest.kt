package com.example.practice.characters

import com.example.practice.repository.CharacterRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
class CharactersViewModelTest {

    @Mock
    lateinit var mockCharactersRepository: CharacterRepository

    @InjectMocks
    lateinit var charactersViewModel: CharactersViewModel

    val testCharactersData = CharacterData.Success(
        listOf(
            Character(
                name = "mom",
                image = "wave.png",
                status = "Alive",
                gender = "Male"
            ),
            Character(
                name = "test",
                image = "wave3.png",
                status = "Alive",
                gender = "Female"
            ),
            Character(
                name = "dad",
                image = "clap.png",
                status = "Dead",
                gender = "Male"
            )
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when refreshCharacters called, then pull characters from repo and inform view`() =
        runTest {
            whenever(mockCharactersRepository.getCharacters("")).thenReturn(testCharactersData)

            charactersViewModel.refreshCharacters()

            verify(mockCharactersRepository).getCharacters("")

            val job = launch(UnconfinedTestDispatcher(testScheduler)) {
                charactersViewModel.characterFlow.collect {
                    assertEquals(testCharactersData, it)
                }
            }

            job.cancel()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when filterCharacters called with filters, then filter the displayed characters`() =
        runTest {
            val testFilter = CharacterFilterType.Gender(female = true)
            val filteredData = listOf(testCharactersData.characters[1])

            whenever(mockCharactersRepository.getCharacters("")).thenReturn(testCharactersData)

            charactersViewModel.filterCharactersByFilterType(testFilter)

            verify(mockCharactersRepository).getCharacters("")

            val job = launch(UnconfinedTestDispatcher(testScheduler)) {
                charactersViewModel.characterFlow.collect {
                    assertEquals(CharacterData.Success(filteredData), it)
                }
            }

            job.cancel()

            val job2 = launch(UnconfinedTestDispatcher(testScheduler)) {
                charactersViewModel.filterFlow.collect {
                    assertEquals(
                        mapOf(
                            Pair("Gender", testFilter),
                            Pair("Status", CharacterFilterType.Status())
                        ), it
                    )
                }
            }

            job2.cancel()
        }
}