package com.example.androidexam.di

import android.content.Context
import androidx.room.Room
import com.example.androidexam.data.local.PersonDatabase
import com.example.androidexam.data.remote.PersonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

/**
 * Created by Ivan Esguerra on 6/11/2024.
 **/
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePersonDatabase(@ApplicationContext context: Context): PersonDatabase {
        return Room.databaseBuilder(
            context,
            PersonDatabase::class.java,
            "person.db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePersonApi(): PersonApi {
        return Retrofit.Builder()
            .baseUrl(PersonApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }
}