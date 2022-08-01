package com.example.serviceassignment.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progress")
data class ProgressEntity(
    @PrimaryKey(autoGenerate = true)
    val id              : Int,
    val fileName        : String,
    val currentProgress : Int
)
