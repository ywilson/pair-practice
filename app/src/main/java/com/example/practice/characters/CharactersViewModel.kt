package com.example.practice.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.practice.pagination.RickMortyPagingSource
import com.example.practice.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Locale.getDefault
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val characterRepository: CharacterRepository) : ViewModel() {
    private val _currentCharacterFlow = MutableStateFlow(Character("", "", ""))

    val currentCharacterFlow = _currentCharacterFlow.asStateFlow()

    private val _filterFlow = MutableStateFlow(
        mapOf(
            Pair(CharacterFilterType.Gender().name, CharacterFilterType.Gender()),
            Pair(CharacterFilterType.Status().name, CharacterFilterType.Status()),
            Pair(CharacterFilterType.Name().name, CharacterFilterType.Name())
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val characterItems : Flow<PagingData<Character>> = _filterFlow
        .flatMapLatest { value ->
            paginateCharactersByFilter(value)
        }.cachedIn(viewModelScope)

    val filterFlow = _filterFlow.asStateFlow()

    fun handleUserEvent(userEvent: CharactersUserEvent) {
        when (userEvent) {
            is CharactersUserEvent.ButtonClick -> {
                _currentCharacterFlow.value = userEvent.characterData
            }
        }
    }

    fun refreshCharacters() = viewModelScope.launch { _filterFlow.emit(_filterFlow.value)}

    fun filterCharactersByFilterType(characterFilter: CharacterFilterType) = viewModelScope.launch(Dispatchers.IO) {
        val characterFilters = _filterFlow.value.toMutableMap()
        characterFilters[characterFilter.name] = characterFilter
        _filterFlow.value = characterFilters.toMap()
    }

    fun paginateCharactersByFilter(filters : Map<String, CharacterFilterType>) : Flow<PagingData<Character>>
    {
        var statusFilter = ""
        var genderFilter = ""
        var nameFilter = ""

        filters.values.forEach {
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

        return Pager(PagingConfig(pageSize = 20)){
            RickMortyPagingSource(characterRepository, nameFilter, genderFilter, statusFilter)
        }.flow
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