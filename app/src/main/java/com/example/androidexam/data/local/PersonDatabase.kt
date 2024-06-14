package com.example.androidexam.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidexam.extension.TypeConverter

/**
 * Created by Ivan Esguerra on 6/11/2024.
 **/
@Database(
    entities = [PersonEntity::class],
    version = 1
)
@TypeConverters(TypeConverter::class)
abstract class PersonDatabase: RoomDatabase() {
    abstract fun getPersonDao(): PersonDao
}