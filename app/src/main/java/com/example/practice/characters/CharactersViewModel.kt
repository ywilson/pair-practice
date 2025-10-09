package com.example.practice.characters

import androidx.lifecycle.ViewModel
import com.example.practice.repository.CharacterRepository
import javax.inject.Inject

class CharactersViewModel @Inject constructor(private val characterRepository: CharacterRepository): ViewModel() {


    fun refreshCharacters() {

    }

}