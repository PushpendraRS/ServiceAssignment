package com.example.serviceassignment

import android.content.Context
import androidx.room.Room
import com.example.serviceassignment.db.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun dbInstance(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        Database::class.java,
        "progressDb"
    ).build()

    @Singleton
    @Provides
    fun getDB(db : Database) = db.getDao()
}