package io.github.kunal26das.assignment.presentation.compose

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import io.github.kunal26das.assignment.R
import io.github.kunal26das.assignment.db.Screenshot

@Composable
fun Note(
    modifier: Modifier = Modifier,
    screenshot: Screenshot?,
    onValueChange: (Screenshot?) -> Unit = {},
) {
    var note by remember { mutableStateOf(screenshot?.note) }
    OutlinedTextField(
        modifier = modifier,
        readOnly = false,
        label = { Text(stringResource(R.string.add_a_note)) },
        value = note.orEmpty(),
        onValueChange = {
            note = it
            onValueChange.invoke(screenshot?.copy(note = note.orEmpty()))
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            autoCorrect = false,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onValueChange.invoke(screenshot?.copy(note = note.orEmpty()))
            }
        )
    )
}