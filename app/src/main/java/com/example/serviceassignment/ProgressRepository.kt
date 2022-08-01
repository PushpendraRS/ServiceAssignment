package com.example.serviceassignment

import com.example.serviceassignment.db.Database
import com.example.serviceassignment.db.ProgressEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ProgressRepository @Inject constructor(private val dao : Database) {

    suspend fun insertProgress(progress : ProgressEntity) {
        dao.getDao().add(progress)
    }

    fun getProgress() : Flow<List<ProgressEntity>>{
       return dao.getDao().getProgress()
    }

    fun getCurrentProgress() : Flow<Int>{
        return dao.getDao().getCurrentProgress()
    }

     fun updateProgress(progress : Int,id : Int) {
        dao.getDao().updateProgress(progress,id)
    }
}