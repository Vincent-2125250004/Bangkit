package com.dicoding.pompomwikie.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.pompomwikie.data.PompomRepository
import com.dicoding.pompomwikie.model.Characters
import com.dicoding.pompomwikie.ui.stateHandler.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class FavoriteViewModel(private val repository: PompomRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Characters>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Characters>>>
        get() = _uiState

    fun getFavoriteCharacters() {
        viewModelScope.launch {
            repository.getFavoritedCharacters().catch {
                _uiState.value = UiState.Error(it.message.toString())
            }.collect {chara ->
                _uiState.value = UiState.Success(chara)
            }
        }
    }

    fun putUpdateChara(id: String, isFavorited: Boolean) {
        repository.putUpdateFavorited(id, isFavorited)
        getFavoriteCharacters()
    }
}
