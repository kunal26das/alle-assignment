package io.github.kunal26das.assignment.presentation.compose

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.github.kunal26das.assignment.db.Screenshot
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screenshot(
    modifier: Modifier = Modifier,
    screenshot: Screenshot? = null,
    onClick: (Screenshot?) -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(2.dp),
        onClick = { onClick.invoke(screenshot) },
        interactionSource = interactionSource,
    ) {
        if (screenshot != null) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                contentDescription = screenshot.path,
                contentScale = ContentScale.Crop,
                model = File(screenshot.path)
            )
        }
    }
}