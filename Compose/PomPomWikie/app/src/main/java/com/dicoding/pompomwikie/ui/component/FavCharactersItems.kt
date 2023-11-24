package com.dicoding.pompomwikie.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dicoding.pompomwikie.R
import com.dicoding.pompomwikie.ui.theme.PomPomWikieTheme

@Composable
fun FavCharactersItem(
    id: String,
    name: String,
    iconUrl: String,
    path: String,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onFavoriteIconClicked: (id: String, isFav: Boolean) -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(100.dp)
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = iconUrl,
                contentDescription = stringResource(id = R.string.contentIcon),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(80.dp)
            )

            Spacer(modifier = modifier.width(20.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(text = path, style = MaterialTheme.typography.titleSmall)
            }

            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = stringResource(id = R.string.add_favorite),
                tint = Color.Red,
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)
                    .clickable { onFavoriteIconClicked(id, !isFavorite) })

        }
    }
}

@Preview()
@Composable
fun FavCharactersItemPreview() {
    PomPomWikieTheme {
        CharactersItem(
            "Arlan",
            "https://static.wikia.nocookie.net/houkai-star-rail/images/a/a9/Character_Arlan_Icon.png/revision/latest?cb=20230723080444",
            "The Destruction",
        )
    }
}