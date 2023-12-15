package io.github.kunal26das.assignment.domain

import android.content.Context
import android.provider.MediaStore
import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.kunal26das.assignment.Constants
import io.github.kunal26das.assignment.db.AppDatabase
import io.github.kunal26das.assignment.db.Screenshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.Closeable

internal class ScreenshotsSource(
    private val context: Context,
) : PagingSource<Int, Screenshot>(), Closeable {

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

    init {
        cursor?.moveToLast()
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Screenshot> {
        if (cursor == null || cursor.isClosed) {
            return LoadResult.Error(IllegalStateException("Cursor is null or closed"))
        }
        val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
        val absolutePathColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
        val relativePathColumn = cursor.getColumnIndex(MediaStore.Images.Media.RELATIVE_PATH)
        val screenshots = mutableListOf<Screenshot>()
        while (cursor.moveToPrevious()) {
            val id = cursor.getInt(idColumn)
            val screenshot = withContext(Dispatchers.IO) {
                AppDatabase.getInstance(context).screenshotDao.getScreenshot(id)
            }
            if (screenshot != null) {
                screenshots.add(screenshot)
                continue
            }
            val absolutePath = cursor.getString(absolutePathColumn)
            val relativePath = cursor.getString(relativePathColumn)
            if (relativePath.contains(Constants.SCREENSHOT, true)) {
                screenshots.add(Screenshot(id = id, path = absolutePath))
            }
            if (screenshots.size == params.loadSize) break
        }
        val page = params.key ?: FIRST_PAGE
        return LoadResult.Page(
            screenshots,
            null,
            if (cursor.isFirst or cursor.isBeforeFirst) null else page - 1,
//            if (cursor.isLast or cursor.isAfterLast) null else page + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Screenshot>) = null

    override fun close() {
        cursor?.close()
    }

    companion object {
        private const val FIRST_PAGE = 0
    }
}