package io.github.kunal26das.assignment.work

import android.content.Context
import android.provider.MediaStore
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import io.github.kunal26das.assignment.Constants
import io.github.kunal26das.assignment.db.AppDatabase
import io.github.kunal26das.assignment.db.Screenshot
import io.github.kunal26das.assignment.usecase.TextRecognitionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScreenshotsWorker(
    private val context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    private val database = AppDatabase.getInstance(context)
    private val textRecognitionUseCase = TextRecognitionUseCase()

    private val cursor = context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.RELATIVE_PATH,
        ),
        null,
        null,
        null,
    )

    override suspend fun doWork(): Result {
        if (cursor == null || cursor.isClosed) {
            return Result.failure()
        }
        val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
        val absolutePathColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
        val relativePathColumn = cursor.getColumnIndex(MediaStore.Images.Media.RELATIVE_PATH)
        withContext(Dispatchers.IO) {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(idColumn)
                var screenshot = AppDatabase.getInstance(context).screenshotDao.getScreenshot(id)
                if (screenshot != null) continue
                val relativePath = cursor.getString(relativePathColumn)
                if (relativePath.contains(Constants.SCREENSHOT, true).not()) {
                    continue
                }
                val absolutePath = cursor.getString(absolutePathColumn)
                screenshot = Screenshot(id = id, path = absolutePath)
                val text = textRecognitionUseCase.process(context, screenshot)
                database.screenshotDao.insert(screenshot.copy(text = text))
            }
        }
        return Result.success()
    }

    companion object {
        fun enqueue(context: Context) {
            val workManager = WorkManager.getInstance(context)
            val workRequest = OneTimeWorkRequestBuilder<ScreenshotsWorker>().build()
            workManager.enqueueUniqueWork(Constants.SCREENSHOT, ExistingWorkPolicy.KEEP, workRequest)
        }
    }
}