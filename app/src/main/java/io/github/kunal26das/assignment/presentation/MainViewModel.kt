package io.github.kunal26das.assignment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import io.github.kunal26das.assignment.db.Screenshot
import io.github.kunal26das.assignment.domain.ScreenshotsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val screenshotsRepository: ScreenshotsRepository
) : ViewModel() {

    private val _screenshot = MutableStateFlow<Screenshot?>(null)
    val screenshot = _screenshot.asStateFlow()

    val screenshotsPager = Pager(
        config = PagingConfig(
            pageSize = LOAD_SIZE,
            initialLoadSize = MAX_LOAD_SIZE,
        ),
        pagingSourceFactory = {
            screenshotsRepository.getScreenshotsFromDb()
        },
    ).flow.cachedIn(viewModelScope)

    fun setScreenshot(
        screenshot: Screenshot,
    ) {
        _screenshot.value = screenshot
    }

    fun update(screenshot: Screenshot) {
        viewModelScope.launch(Dispatchers.IO) {
            screenshotsRepository.updateScreenshot(screenshot)
        }
    }

    companion object {
        private const val LOAD_SIZE = 10
        private const val MAX_LOAD_SIZE = 10
    }
}