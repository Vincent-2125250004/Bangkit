package com.dicoding.pompomwikie.ui.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.pompomwikie.R
import com.dicoding.pompomwikie.ViewModelFactory
import com.dicoding.pompomwikie.di.Injection
import com.dicoding.pompomwikie.model.Characters
import com.dicoding.pompomwikie.ui.component.FavCharactersItem
import com.dicoding.pompomwikie.ui.stateHandler.UiState

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateToDetail: (String) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteCharacters()
            }

            is UiState.Success -> {
                FavoriteContent(
                    state = uiState.data,
                    onFavoritedClick = { id, isFav ->
                        viewModel.putUpdateChara(id, isFav)
                    },
                    navigateToDetail = navigateToDetail
                )
            }

            is UiState.Error -> {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteContent(
    modifier: Modifier = Modifier,
    state: List<Characters>,
    onFavoritedClick: (id: String, isFav: Boolean) -> Unit,
    navigateToDetail: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.menu_fav),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(state, key = { it.id }) { data ->
                    FavCharactersItem(
                        id = data.id,
                        name = data.name,
                        iconUrl = data.iconChara,
                        path = data.path,
                        modifier = modifier.clickable {
                            navigateToDetail(data.id)
                        },
                        isFavorite = data.isFavorited,
                        onFavoriteIconClicked = { id, isFav ->
                            onFavoritedClick(id, isFav)
                        }
                    )
            }
        }

    }
}
