package com.example.serviceassignment

import androidx.lifecycle.ViewModel
import com.example.serviceassignment.db.ProgressEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(private val repo : ProgressRepository) : ViewModel() {

     suspend fun insertProgress(progress : ProgressEntity){
       repo.insertProgress(progress)
    }

    fun updateProgress(progress : Int,id : Int) {
        repo.updateProgress(progress,id)
    }

    fun getProgress() : Flow<List<ProgressEntity>>{
        return repo.getProgress()
    }

    fun getCurrentProgress() : Flow<Int> {
        return repo.getCurrentProgress()
    }
}