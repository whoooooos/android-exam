package com.example.androidexam.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.androidexam.extension.TypeConverter
import kotlinx.parcelize.Parcelize

/**
 * Created by Ivan Eguerra on 6/11/2024.
 **/
@Entity
@Parcelize
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cell: String,
    @TypeConverters(TypeConverter::class)
    val dob: Dob,
    val email: String,
    val gender: String,
    @TypeConverters(TypeConverter::class)
    val location: Location,
    @TypeConverters(TypeConverter::class)
    val name: Name,
    @TypeConverters(TypeConverter::class)
    val picture: Picture,
): Parcelable {
    @Parcelize
    data class Dob(
        val age: Int,
        val date: String
    ): Parcelable

    @Parcelize
    data class Location(
        val city: String,
        val country: String,
        val state: String,
        val street: Street,
    ): Parcelable {
        @Parcelize
        data class Street(
            val name: String,
            val number: Int
        ): Parcelable
    }
    @Parcelize
    data class Name(
        val first: String,
        val last: String,
        val title: String
    ): Parcelable
    @Parcelize
    data class Picture(
        val large: String,
        val medium: String,
        val thumbnail: String
    ): Parcelable
}
