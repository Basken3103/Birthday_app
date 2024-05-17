package com.example.birthdayapp2_0.Reposority

import retrofit2.Call
import com.example.birthdayapp2_0.models.Person
import retrofit2.http.*

interface PersonService {

    @GET("persons")
    fun getByUserId(@Query("user_id")userid:String): Call<List<Person>>

    @GET("persons/{personId}")
    fun GetPersonsById(@Path("personId") personId: Int): Call<Person>

    @POST("persons")
    fun savePersons(@Body person: Person): Call<Person>

    @DELETE("persons/{id}")
    fun delete(@Path("id") id: Int): Call<Person>

    @PUT("persons/{id}")
    fun updatePerson(@Path("id") id: Int, @Body person: Person): Call<Person>
}