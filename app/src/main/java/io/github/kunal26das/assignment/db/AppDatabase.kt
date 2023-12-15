package io.github.kunal26das.assignment.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Screenshot::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val screenshotDao: ScreenshotDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    context.packageName
                ).build()
            }
            return INSTANCE!!
        }
    }
}