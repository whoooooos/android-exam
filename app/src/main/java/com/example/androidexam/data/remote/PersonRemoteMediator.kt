package com.example.androidexam.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.androidexam.data.local.PersonDatabase
import com.example.androidexam.data.local.PersonEntity
import com.example.androidexam.data.mappers.toPersonEntity
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Ivan Esguerra on 6/11/2024.
 **/
@OptIn(ExperimentalPagingApi::class)
class PersonRemoteMediator(
    private val personDb: PersonDatabase,
    private val personApi: PersonApi
): RemoteMediator<Int, PersonEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PersonEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val persons = personApi.getPersons(
                page = loadKey
            ).results

            personDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    personDb.getPersonDao().clearAll()
                }
                val personEntities = persons.map { it.toPersonEntity() }
                personDb.getPersonDao().upsertAll(personEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = persons.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}