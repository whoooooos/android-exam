package com.example.androidexam.extension

import androidx.room.TypeConverter
import com.example.androidexam.data.local.PersonEntity
import com.google.gson.Gson

/**
 * Created by Ivan Esguerra on 6/11/2024.
 **/
var gson = Gson()
class TypeConverter {
    @TypeConverter
    fun dobToJson(dob: PersonEntity.Dob): String {
        return gson.toJson(dob)
    }

    @TypeConverter
    fun stringToDobObject(data: String): PersonEntity.Dob {
        return gson.fromJson(data, PersonEntity.Dob::class.java)
    }

    @TypeConverter
    fun locationToJson(location: PersonEntity.Location): String {
        return gson.toJson(location)
    }

    @TypeConverter
    fun stringToLocObject(data: String): PersonEntity.Location {
        return gson.fromJson(data, PersonEntity.Location::class.java)
    }


    @TypeConverter
    fun nameToJson(location: PersonEntity.Name): String {
        return gson.toJson(location)
    }

    @TypeConverter
    fun stringToNameObject(data: String): PersonEntity.Name {
        return gson.fromJson(data, PersonEntity.Name::class.java)
    }

    @TypeConverter
    fun pictureToJson(location: PersonEntity.Picture): String {
        return gson.toJson(location)
    }

    @TypeConverter
    fun stringToPicObject(data: String): PersonEntity.Picture {
        return gson.fromJson(data, PersonEntity.Picture::class.java)
    }
}