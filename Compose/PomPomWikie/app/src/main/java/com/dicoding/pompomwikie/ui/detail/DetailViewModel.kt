package com.dicoding.pompomwikie.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.pompomwikie.data.PompomRepository
import com.dicoding.pompomwikie.model.Characters
import com.dicoding.pompomwikie.ui.stateHandler.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: PompomRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Characters>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Characters>>
        get() = _uiState


    fun getCharactersById(id: String) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getCharactersById(id))
    }

    fun putUpdateCharacter(id: String, isFavorited: Boolean) = viewModelScope.launch {
        repository.putUpdateFavorited(id, !isFavorited).collect { isFavorite ->
            if (isFavorite) getCharactersById(id)
        }

    }


}
