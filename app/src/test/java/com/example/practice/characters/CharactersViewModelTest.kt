package com.example.practice.characters

import com.example.practice.repository.CharacterRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    @Mock
    lateinit var mockCharactersRepository: CharacterRepository

    @InjectMocks
    lateinit var charactersViewModel: CharactersViewModel

    @Test
    fun `when refreshCharacters called, then pull characters from repo and inform view`()
    {

        charactersViewModel.refreshCharacters()
    }
}