package com.dicoding.pompomwikie.ui.detail

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.pompomwikie.R
import com.dicoding.pompomwikie.ViewModelFactory
import com.dicoding.pompomwikie.di.Injection
import com.dicoding.pompomwikie.ui.stateHandler.UiState


@Composable
fun DetailScreen(
    id: String,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToBack: () -> Unit,
) {

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getCharactersById(id)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    fullImgUrl = data.fullImgChara,
                    description = data.description,
                    id = data.id,
                    path = data.path,
                    name = data.name,
                    rarity = data.rarity,
                    element = data.element,
                    onBackClick = {
                        navigateToBack()
                    },
                    isFavorite = data.isFavorited,
                    onFavoriteClick = { id, isFav ->
                        viewModel.putUpdateCharacter(id, isFav)
                    }
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    fullImgUrl: String,
    description: String,
    id: String,
    path: String,
    name: String,
    rarity: String,
    element: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onFavoriteClick: (id: String, isFav: Boolean) -> Unit
) {
    Log.d("IsFavorite", "DetailContent: $id")
    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                AsyncImage(
                    model = fullImgUrl,
                    contentDescription = stringResource(id = R.string.splashArt),
                    contentScale = ContentScale.Crop,
                    modifier = modifier.height(500.dp)
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.moveBack),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    tint = Color.Red,
                    contentDescription = stringResource(id = R.string.add_favorite),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .size(48.dp)
                        .clickable {
                            onFavoriteClick(id, isFavorite)
                        }
                        .testTag("favorite")
                )

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 24.sp
                    ),
                )
                Row(modifier = Modifier) {
                    Text(
                        text = rarity,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 16.sp
                        ),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = path,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.sp
                        ),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = element,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.sp
                        ),
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
                Text(
                    text = description,
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 14.sp
                    ),
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DetailContentPreview() {
//    PomPomWikieTheme {
//        DetailContent(
//            fullImgUrl = "",
//            description = stringResource(id = R.string.dummy_text),
//            path = "The Perservation",
//            name = "Fu Xuan",
//            rarity = "⭐⭐⭐⭐⭐",
//            element = "Quantum",
//            onBackClick = {},
//            isFavorite = true,
//            onFavoriteClick = {},
//            id = ""
//        )
//    }
//}

