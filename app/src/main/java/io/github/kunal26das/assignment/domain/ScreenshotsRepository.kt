package io.github.kunal26das.assignment.domain

import android.content.Context
import androidx.paging.PagingSource
import io.github.kunal26das.assignment.db.AppDatabase
import io.github.kunal26das.assignment.db.Screenshot

class ScreenshotsRepository(private val context: Context) {

    private val appDatabase = AppDatabase.getInstance(context)

    fun getScreenshotsFromLocalStorage(): PagingSource<Int, Screenshot> {
        return ScreenshotsSource(context)
    }

    fun getScreenshotsFromDb(): PagingSource<Int, Screenshot> {
        return appDatabase.screenshotDao.getScreenshots()
    }

    fun updateScreenshot(screenshot: Screenshot) {
        appDatabase.screenshotDao.update(screenshot)
    }
}