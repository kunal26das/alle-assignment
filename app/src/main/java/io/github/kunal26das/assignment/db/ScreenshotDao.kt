package io.github.kunal26das.assignment.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface ScreenshotDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(screenshot: Screenshot)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(screenshot: Screenshot)

    @Query("SELECT * FROM Screenshot WHERE id == :id LIMIT 1")
    fun getScreenshot(id: Int): Screenshot?

    @Query("SELECT * FROM Screenshot ORDER BY id DESC")
    fun getScreenshots(): PagingSource<Int, Screenshot>
}