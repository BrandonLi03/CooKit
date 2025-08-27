package com.example.cookit.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    @ColumnInfo(name = "mealId")
    val mealId: String?,
    @ColumnInfo(name = "mealName")
    val mealName: String?,
    @ColumnInfo(name = "mealThumb")
    val mealThumb: String?
)