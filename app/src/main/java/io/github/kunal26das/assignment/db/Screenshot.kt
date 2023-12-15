package io.github.kunal26das.assignment.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Screenshot(
    @PrimaryKey val id: Int,
    @ColumnInfo("path") val path: String,
    @ColumnInfo("text") val text: String = "",
    @ColumnInfo("note") val note: String = "",
)

