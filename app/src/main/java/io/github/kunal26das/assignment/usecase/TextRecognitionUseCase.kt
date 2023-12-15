package io.github.kunal26das.assignment.usecase

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import io.github.kunal26das.assignment.db.Screenshot
import kotlinx.coroutines.tasks.await
import java.io.File

class TextRecognitionUseCase {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    suspend fun process(
        context: Context,
        screenshot: Screenshot,
    ): String {
        val file = File(screenshot.path)
        val uri = Uri.fromFile(file)
        val image = InputImage.fromFilePath(context, uri)
        val result = recognizer.process(image).await()
        return result.text
    }
}

