package com.example.androidexam.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

/**
 * Created by Ivan Esguerra on 6/11/2024.
 **/
@Dao
interface PersonDao {
    @Upsert
    fun upsertAll(persons: List<PersonEntity>)

    @Query("SELECT * FROM personentity")
    fun getPersons(): PagingSource<Int, PersonEntity>

    @Query("DELETE FROM personentity")
    fun clearAll()
}