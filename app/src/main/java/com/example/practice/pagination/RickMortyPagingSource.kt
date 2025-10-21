package com.example.practice.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.practice.characters.Character
import com.example.practice.characters.CharacterData
import com.example.practice.repository.CharacterRepository
import java.lang.Exception

class RickMortyPagingSource(
    private val characterRepository: CharacterRepository,
    private val nameFilter: String = "",
    private val genderFilter: String = "",
    private val statusFilter: String = ""
) : PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        try {
            val currentPage = params.key ?: 1
            val response = characterRepository.getCharacters(nameFilter, genderFilter, statusFilter, currentPage)
            if (response is CharacterData.Success) {
                val items = response.characters

                return LoadResult.Page(
                    data = items,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (items.isEmpty()) null else currentPage + 1
                )
            }
            else
                return LoadResult.Invalid()

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}