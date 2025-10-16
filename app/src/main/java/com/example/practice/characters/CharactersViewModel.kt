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
    private val _characterFlow = MutableStateFlow<CharacterData>(CharacterData.Loading)
    val characterFlow = _characterFlow.asStateFlow()

    private val _currentCharacterFlow = MutableStateFlow(Character("", "", ""))

    val currentCharacterFlow = _currentCharacterFlow.asStateFlow()
    
    fun refreshCharacters() = viewModelScope.launch(Dispatchers.IO) {
        _characterFlow.value = characterRepository.getCharacters("")
    }

    fun handleUserEvent(userEvent: CharactersUserEvent)
    {
        when (userEvent)
        {
            is CharactersUserEvent.ButtonClick -> {_currentCharacterFlow.value = userEvent.characterData}
        }
    }
}

sealed interface CharactersUserEvent
{
    data class ButtonClick(val characterData: Character) : CharactersUserEvent
}