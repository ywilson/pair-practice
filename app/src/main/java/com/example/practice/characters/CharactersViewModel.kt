package com.example.practice.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale.getDefault
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val characterRepository: CharacterRepository) : ViewModel() {
    private val _characterFlow = MutableStateFlow<CharacterData>(CharacterData.Loading)
    val characterFlow = _characterFlow.asStateFlow()

    private val _currentCharacterFlow = MutableStateFlow(Character("", "", ""))

    val currentCharacterFlow = _currentCharacterFlow.asStateFlow()

    private val _filterFlow = MutableStateFlow(
        mapOf(
            Pair(CharacterFilterType.Gender().name, CharacterFilterType.Gender()),
            Pair(CharacterFilterType.Status().name, CharacterFilterType.Status()),
            Pair(CharacterFilterType.Name().name, CharacterFilterType.Name())
        )
    )

    val filterFlow = _filterFlow.asStateFlow()

    fun refreshCharacters() = viewModelScope.launch(Dispatchers.IO) {
        _characterFlow.value = characterRepository.getCharacters("")
    }

    fun handleUserEvent(userEvent: CharactersUserEvent) {
        when (userEvent) {
            is CharactersUserEvent.ButtonClick -> {
                _currentCharacterFlow.value = userEvent.characterData
            }
        }
    }

    fun filterCharactersByFilterType(characterFilter: CharacterFilterType) = viewModelScope.launch(Dispatchers.IO) {

        val characterFilters = _filterFlow.value.toMutableMap()
        characterFilters[characterFilter.name] = characterFilter

        var statusFilter = ""
        var genderFilter = ""
        var nameFilter = ""

        characterFilters.values.forEach {
            when (it) {
                is CharacterFilterType.Gender -> {
                    genderFilter = it.toFilterString()
                }

                is CharacterFilterType.Status -> {
                    statusFilter = it.toFilterString()
                }

                is CharacterFilterType.Name -> {
                    nameFilter = it.toFilterString()
                }
            }
        }

        _characterFlow.value = characterRepository.getCharacters(nameFilter, genderFilter, statusFilter)

//        val characterData = characterRepository.getCharacters("")
//        if (characterData is CharacterData.Success) {
//            val characterList = characterData.characters
//            _characterFlow.value = CharacterData.Success(characterList.filter { character ->
//                characterFilters.values.forEach { filterType ->
//                    if (!filterType.passesFilter(character))
//                        return@filter false
//                }
//                return@filter true
//            })
//        }

        _filterFlow.value = characterFilters.toMap()

        println("Test " + _filterFlow.value)
    }
}

sealed interface CharactersUserEvent {
    data class ButtonClick(val characterData: Character) : CharactersUserEvent
}

sealed interface CharacterFilterType {
    val name: String

    fun passesFilter(characterToCheck: Character): Boolean

    fun toFilterString(): String
    data class Gender(val male: Boolean = false, val female: Boolean = false) : CharacterFilterType {
        override val name: String
            get() = "Gender"

        override fun passesFilter(characterToCheck: Character): Boolean {
            var passes = false
            if (!male && !female)
                passes = true

            if (male)
                passes = characterToCheck.gender.lowercase(getDefault()) == "male"
            if (female)
                passes = passes || characterToCheck.gender.lowercase(getDefault()) == "female"
            return passes
        }

        override fun toFilterString(): String {
            if (male)
                return "male"
            if (female)
                return "female"
            return ""
        }
    }

    data class Status(val alive: Boolean = false, val dead: Boolean = false, val unknown: Boolean = false) :
        CharacterFilterType {
        override val name: String
            get() = "Status"

        override fun passesFilter(characterToCheck: Character): Boolean {
            var passes = false
            if (!alive && !dead && !unknown)
                passes = true

            if (alive)
                passes = characterToCheck.status.lowercase(getDefault()) == "alive"
            if (dead)
                passes = passes || characterToCheck.status.lowercase(getDefault()) == "dead"
            if (unknown)
                passes = passes || characterToCheck.status.lowercase(getDefault()) == "unknown"
            return passes
        }

        override fun toFilterString(): String {
            if (alive)
                return "alive"
            if (dead)
                return "dead"
            if (unknown)
                return "unknown"
            return ""
        }
    }

    data class Name(val searchName: String = "") : CharacterFilterType {
        override val name: String
            get() = "Name"

        override fun passesFilter(characterToCheck: Character): Boolean {
            return true
        }

        override fun toFilterString(): String {
            return searchName
        }

    }
}