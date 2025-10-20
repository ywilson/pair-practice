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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when refreshCharacters called, then pull characters from repo and inform view`() =
     runTest {
        val testCharactersData = CharacterData.Success(listOf(
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
                gender = "Male"
            ),
            Character(
                name = "dad",
                image = "clap.png",
                status = "Dead",
                gender = "Male"
            )
        ))
        whenever(mockCharactersRepository.getCharacters("")).thenReturn(testCharactersData)

        charactersViewModel.refreshCharacters()

        verify(mockCharactersRepository).getCharacters("")

        val job = launch (UnconfinedTestDispatcher(testScheduler)){charactersViewModel.characterFlow.collect {
            assertEquals(testCharactersData, it)
        }}

         job.cancel()
    }
}