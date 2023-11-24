package com.dicoding.pompomwikie.data

import com.dicoding.pompomwikie.model.Characters
import com.dicoding.pompomwikie.model.CharactersData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf


class PompomRepository {

    private val characters = mutableListOf<Characters>()


    init {
        if (characters.isEmpty()) {
            CharactersData.characters.forEach {
                characters.add(
                    it
                )
            }
        }
    }

    fun getAllCharacters(): Flow<List<Characters>> {
        return flowOf(characters)
    }

    fun getCharactersById(id: String): Characters {
        return characters.first {
            it.id == id
        }
    }

    fun searchCharacters(query: String): Flow<List<Characters>> {
        return flow {
            val filteredCharacters = characters.filter {
                it.name.contains(query, ignoreCase = true)
            }
            emit(filteredCharacters)
        }
    }

    fun getFavoritedCharacters(): Flow<List<Characters>> {
        return flowOf(characters.filter { it.isFavorited })
    }

    fun putUpdateFavorited(id: String, isFavorited: Boolean): Flow<Boolean> {
        val index = characters.indexOfFirst { it.id == id }
        val result = if (index >= 0) {
            val chara = characters[index]
            characters[index] = chara.copy(isFavorited = isFavorited)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: PompomRepository? = null

        fun getInstance(): PompomRepository =
            instance ?: synchronized(this) {
                PompomRepository().apply {
                    instance = this
                }
            }
    }


}


