package com.dicoding.pompomwikie.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.pompomwikie.ViewModelFactory
import com.dicoding.pompomwikie.di.Injection
import com.dicoding.pompomwikie.model.Characters
import com.dicoding.pompomwikie.ui.component.CharactersItem
import com.dicoding.pompomwikie.ui.component.ScrollToTopButton
import com.dicoding.pompomwikie.ui.component.Search
import com.dicoding.pompomwikie.ui.stateHandler.UiState
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateToDetail: (String) -> Unit
) {

    val query by viewModel.query
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val showButton: Boolean by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getCharacters()
            }

            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = { newQuery ->
                        viewModel.search(newQuery)
                    },
                    listState = listState,
                    characters = uiState.data,
                    showButton = showButton,
                    onClickScroll = {
                        scope.launch {
                            listState.animateScrollToItem(index = 0)
                        }
                    },
                    modifier = modifier,
                    navigateToDetail = navigateToDetail
                )
                LaunchedEffect(query) {
                    if (query.isEmpty()) {
                        listState.animateScrollToItem(0)
                    }
                }
            }

            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    listState: LazyListState,
    characters: List<Characters>,
    showButton: Boolean,
    onClickScroll: () -> Unit,
    navigateToDetail: (String) -> Unit
) {
    Box(modifier = modifier) {
        Column {
            Search(
                query = query,
                onQueryChange = {
                    onQueryChange(it)
                },
                modifier = Modifier.background(
                    MaterialTheme.colorScheme.primary
                )
            )
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f).testTag("CharaList"),
                state = listState
            ) {
                items(characters, key = { it.id }) { data ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navigateToDetail(data.id)
                            }
                            .animateItemPlacement(tween(durationMillis = 300))
                    ) {
                        CharactersItem(
                            name = data.name,
                            iconUrl = data.iconChara,
                            path = data.path,
                            modifier = modifier
                        )
                    }
                }

            }
        }
        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(onClick = {
                onClickScroll()
            })
        }

    }

}
