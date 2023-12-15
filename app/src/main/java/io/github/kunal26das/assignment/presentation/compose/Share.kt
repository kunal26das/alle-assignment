package io.github.kunal26das.assignment.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import io.github.kunal26das.assignment.presentation.MainViewModel
import java.io.File

@Composable
fun ShareContent(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SelectedScreenshot(
            modifier = modifier.weight(1f),
            viewModel = viewModel
        )
        Screenshots(
            modifier = Modifier.height(128.dp),
            viewModel = viewModel
        )
    }
}

@Composable
private fun SelectedScreenshot(
    modifier: Modifier,
    viewModel: MainViewModel,
) {
    val selectedScreenshot by viewModel.screenshot.collectAsState()
    AsyncImage(
        modifier = modifier
            .aspectRatio(9 / 16f)
            .padding(8.dp),
        contentDescription = selectedScreenshot?.path,
        contentScale = ContentScale.Crop,
        model = File(selectedScreenshot?.path.orEmpty())
    )
}

@Composable
private fun Screenshots(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
) {
    val screenshots = viewModel.screenshotsPager.collectAsLazyPagingItems()
    HorizontalScreenshots(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 2.dp),
        screenshotPadding = PaddingValues(horizontal = 2.dp),
        screenshots = screenshots,
        onClick = {
            if (it != null) {
                viewModel.setScreenshot(it)
            }
        },
    )
}