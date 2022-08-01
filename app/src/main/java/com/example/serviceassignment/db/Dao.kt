package com.example.serviceassignment.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

  @Insert
  suspend fun add(progress : ProgressEntity)

  @Query("Select * from progress")
  fun getProgress() : Flow<List<ProgressEntity>>

  @Query("Select currentProgress from progress")
  fun getCurrentProgress() : Flow<Int>

  @Query("UPDATE progress SET currentProgress=:progress WHERE id=:id")
  fun updateProgress(progress : Int,id : Int)

}