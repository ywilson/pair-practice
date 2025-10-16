package com.example.practice.characters

import android.content.Context
import android.widget.Toast
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
class CharactersViewModel @Inject constructor(private val characterRepository: CharacterRepository, private val context: Context): ViewModel() {
    private val _characterFlow = MutableStateFlow<CharacterData>(CharacterData.Loading)
    val characterFlow = _characterFlow.asStateFlow()
    
    fun refreshCharacters() = viewModelScope.launch(Dispatchers.IO) {
        _characterFlow.value = characterRepository.getCharacters("")
    }

    fun handleUserEvent(userEvent: CharactersUserEvent)
    {
        when (userEvent)
        {
            is CharactersUserEvent.ButtonClick -> Toast.makeText(context, userEvent.characterData.name, Toast.LENGTH_LONG).show()
        }
    }
}

sealed interface CharactersUserEvent
{
    data class ButtonClick(val characterData: Character) : CharactersUserEvent
}