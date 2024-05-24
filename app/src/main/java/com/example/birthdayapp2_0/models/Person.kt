package com.example.birthdayapp2_0.models


import java.io.Serializable

data class Person(
    var id: Int,
    var name: String,
    var age: Int,
    var birthDate: Int,
    var birthMonth: Int,
    var birthYear: Int,
    var userId: String,


  ) :

        Serializable {
        constructor(
        name: String,
        age: Int,
        birthDate: Int,
        birthMonth: Int,
        birthYear: Int,
        E_mail: String,
    ) :
            this(-1, name, age, birthDate, birthMonth, birthYear, E_mail)

    override fun toString(): String {
        return "$id, $name, $age, $birthDate, $birthMonth, $birthYear, $userId"

    }
}
