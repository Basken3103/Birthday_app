package com.example.birthdayapp2_0.models

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.birthdayapp2_0.Repository.PersonsRepository
import com.google.firebase.auth.FirebaseAuth



class PersonViewmodel : ViewModel() {
    private val repository = PersonsRepository()
    val personsLiveData: LiveData<List<Person>> = repository.personLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData


    init {
        val email = FirebaseAuth.getInstance().currentUser?.email
        email?.let {
            reload(it)
        }

    }

    fun reload(user_id: String) {
        Log.d("APPLE", "Reloading Data for user:$user_id")
        repository.getPersonByUserId(user_id)
    }



    operator fun get(index: Int): Person? {
        return personsLiveData.value?.get(index)
    }

    fun getPersonById(user_id: String) {
        repository.getPersonByUserId(user_id)
    }


    fun add(person: Person) {
        repository.add(person)

    }

    fun delete(id: Int) {
        repository.delete(id)
        Log.d("APPLE", "Deleted person with id: $id")
    }

    fun update(person: Person) {
        repository.update(person)
        Log.d("APPLE", "Updated person: $person")
    }

    fun sortByName() {
        repository.sortByName()
    }

    fun sortByAge() {
        repository.sortByAge()
    }


    fun sortByBirthday() {
        repository.sortByBirthday()
    }


    fun filterByName(name: String) {
        repository.filterByName(name)
    }

    private var userEmail: String = ""
    fun getUserEmail(): String {
        return userEmail
    }

    fun setUserEmail(email: String) {
        userEmail = email
    }



}




