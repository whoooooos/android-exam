package com.example.androidexam.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.androidexam.data.local.PersonDatabase
import com.example.androidexam.data.local.PersonEntity
import com.example.androidexam.data.remote.PersonApi
import com.example.androidexam.data.remote.PersonRemoteMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ivan Esguerra on 6/11/2024.
 **/
const val PAGE_SIZE = 10

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val personApi: PersonApi,
    private val personDatabase: PersonDatabase,
): ViewModel() {
    @OptIn(ExperimentalPagingApi::class)
    fun getPersons(): Flow<PagingData<PersonEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
            ),
            pagingSourceFactory = {
                personDatabase.getPersonDao().getPersons()
            },
            remoteMediator = PersonRemoteMediator(
                personDatabase,
                personApi,
            )
        ).flow.cachedIn(viewModelScope)
}