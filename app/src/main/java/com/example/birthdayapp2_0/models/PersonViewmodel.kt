package com.example.birthdayapp2_0.models



import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.birthdayapp2_0.Reposority.PersonsRepository



class PersonViewmodel : ViewModel() {
    private val repository = PersonsRepository()
    val personsLiveData: LiveData<List<Person>> = repository.personLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData


    init {
       reload(user_id = String())
    }

    fun reload(user_id: String) {
        repository.getPersonByUserId(user_id)
    }



    operator fun get(index: Int): Person? {
        return personsLiveData.value?.get(index)
    }

    fun getPersonByUserId(user_id: String) {
        repository.getPersonByUserId(user_id)
    }


    fun add(person: Person) {
        repository.add(person)

    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    fun update(person: Person) {
        repository.update(person)
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




