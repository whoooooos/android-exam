package com.example.androidexam.data.mappers

import com.example.androidexam.data.local.PersonEntity
import com.example.androidexam.data.remote.PersonDto

/**
 * Created by Ivan Esguerra on 6/11/2024.
 **/
fun PersonDto.Result.toPersonEntity(): PersonEntity {
    return PersonEntity(
        cell = cell,
        dob = PersonEntity.Dob(dob.age, dob.date),
        email = email,
        gender = gender ,
        location = PersonEntity.Location(location.city, location.country, location.state, PersonEntity.Location.Street(location.street.name, location.street.number)),
        name = PersonEntity.Name(name.first, name.last, name.title) ,
        picture = PersonEntity.Picture(picture.large, picture.medium,picture.thumbnail),
    )
}