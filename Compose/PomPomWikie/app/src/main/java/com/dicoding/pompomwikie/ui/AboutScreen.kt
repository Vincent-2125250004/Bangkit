package com.dicoding.pompomwikie.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.pompomwikie.R
import com.dicoding.pompomwikie.ui.theme.PomPomWikieTheme


@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Image(
                painter = painterResource(id = R.drawable.vincent),
                contentDescription = null,
                modifier = Modifier
                    .padding(100.dp)
                    .clip(CircleShape)
            )
            Text(
                text = stringResource(id = R.string.name),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 24.sp
                )
            )
            Text(
                text = stringResource(id = R.string.email),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 24.sp
                )
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    PomPomWikieTheme {
        AboutScreen()
    }
}