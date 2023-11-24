package com.dicoding.pompomwikie.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.pompomwikie.data.PompomRepository
import com.dicoding.pompomwikie.model.Characters
import com.dicoding.pompomwikie.ui.stateHandler.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PompomRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Characters>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<Characters>>> get() = _uiState


    fun getCharacters() {
        viewModelScope.launch {
            repository.getAllCharacters()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { characters ->
                    _uiState.value = UiState.Success(characters)
                }
        }
    }

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query


    fun search(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            repository.searchCharacters(_query.value).collect { characters ->
                _uiState.value = UiState.Success(characters)
            }
        }
    }
}
