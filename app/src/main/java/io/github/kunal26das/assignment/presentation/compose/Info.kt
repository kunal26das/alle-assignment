package io.github.kunal26das.assignment.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.github.kunal26das.assignment.R
import io.github.kunal26das.assignment.presentation.MainViewModel
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun InfoContent(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
) {
    val screenshot by viewModel.screenshot.collectAsState()
    LazyColumn(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (screenshot != null) {
            item {
                AsyncImage(
                    modifier = Modifier
                        .height(512.dp)
                        .aspectRatio(9 / 16f)
                        .padding(4.dp),
                    contentDescription = screenshot?.path,
                    contentScale = ContentScale.FillWidth,
                    model = File(screenshot?.path.orEmpty())
                )
            }
            item {
                Note(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                    screenshot = screenshot
                ) {
                    if (it != null) {
                        viewModel.update(it)
                        viewModel.setScreenshot(it)
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.padding(start = 8.dp, top = 20.dp, end = 8.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.description),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    SelectionContainer {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            text = screenshot?.text.orEmpty(),
                            fontSize = 12.sp,
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(256.dp))
            }
        }
    }
}