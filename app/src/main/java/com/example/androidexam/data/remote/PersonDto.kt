package com.example.androidexam.data.remote

/**
 * Created by Ivan Esguerra on 6/11/2024.
 **/
data class PersonDto(
    val info: Info,
    val results: List<Result>
) {
    data class Info(
        val page: Int,
        val results: Int,
        val seed: String,
        val version: String
    )
    data class Result(
        val cell: String,
        val dob: Dob,
        val email: String,
        val gender: String,
        val location: Location,
        val name: Name,
        val id: Id,
        val picture: Picture,
    ) {
        data class Dob(
            val age: Int,
            val date: String
        )
        data class Location(
            val city: String,
            val country: String,
            val state: String,
            val street: Street,
        ) {
            data class Street(
                val name: String,
                val number: Int
            )
        }
        data class Name(
            val first: String,
            val last: String,
            val title: String
        )
        data class Id(
            val name: String,
            val value: String
        )
        data class Picture(
            val large: String,
            val medium: String,
            val thumbnail: String
        )
    }
}

