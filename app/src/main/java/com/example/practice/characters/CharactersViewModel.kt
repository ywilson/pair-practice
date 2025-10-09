package com.example.practice.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(private val characterRepository: CharacterRepository): ViewModel() {
    private val _characterFlow = MutableStateFlow<List<Character>>(emptyList())
    val characterFlow = _characterFlow.asStateFlow()
    
    fun refreshCharacters() = viewModelScope.launch(Dispatchers.IO) {
        _characterFlow.value = characterRepository.getCharacters("")
    }
}