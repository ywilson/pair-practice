package com.example.practice.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.Locale.getDefault
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val characterRepository: CharacterRepository): ViewModel() {
    private val _characterFlow = MutableStateFlow<CharacterData>(CharacterData.Loading)
    val characterFlow = _characterFlow.asStateFlow()

    private val _currentCharacterFlow = MutableStateFlow(Character("", "", ""))

    val currentCharacterFlow = _currentCharacterFlow.asStateFlow()

    private val _filterFlow = MutableStateFlow(listOf<CharacterFilterType>())

    val filterFlow = _filterFlow.asStateFlow()
    
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

    fun filterCharacters(characterFilters : List<CharacterFilterType>) = viewModelScope.launch(Dispatchers.IO){
        val characterData = characterRepository.getCharacters("")
        if (characterData is CharacterData.Success)
        {
            val characterList = characterData.characters
            _characterFlow.value = CharacterData.Success(characterList.filter { character ->
                var filteredOut = false
                characterFilters.forEach { filterType ->
                    when (filterType)
                    {
                        is CharacterFilterType.Gender -> {
                            val gender = character.gender.lowercase(getDefault())
                            if (filterType.male)
                                filteredOut = gender != "male"
                            if (filterType.female)
                                filteredOut = gender != "female"
                        }
                        is CharacterFilterType.Status -> {
                            val status = character.status.lowercase(getDefault())
                            if (filterType.alive)
                                filteredOut = status != "alive"
                            if (filterType.dead)
                                filteredOut = status != "dead"
                            if (filterType.unknown)
                                filteredOut = status != "unknown"
                        }
                    }
                }
                return@filter !filteredOut
            })
        }
    }
}

sealed interface CharactersUserEvent
{
    data class ButtonClick(val characterData: Character) : CharactersUserEvent
}

sealed interface CharacterFilterType
{
    data class Gender(val male : Boolean = false, val female : Boolean = false) : CharacterFilterType
    data class Status(val alive : Boolean = false, val dead : Boolean = false, val unknown : Boolean = false) : CharacterFilterType
}