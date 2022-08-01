package com.example.serviceassignment.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [ProgressEntity::class], version = 1)
abstract class Database : RoomDatabase(){
    abstract fun getDao() : Dao
}