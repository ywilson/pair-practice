package com.example.practice.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val characterRepository: CharacterRepository): ViewModel() {
    private val _characterFlow = MutableStateFlow<List<Character>>(emptyList())
    val characterFlow = _characterFlow.asStateFlow()
    
    fun refreshCharacters() = viewModelScope.launch(Dispatchers.IO) {
        when (val charData = characterRepository.getCharacters(""))
        {
            is CharacterData.Success -> _characterFlow.value = charData.characters
            is CharacterData.Error -> {}
            is CharacterData.Loading -> {}
        }
    }
}