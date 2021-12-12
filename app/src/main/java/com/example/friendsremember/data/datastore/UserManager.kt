package com.example.friendsremember.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager private constructor(private val dataStore: DataStore<Preferences>) {

    //Create some keys
    companion object {
        val USER_AGE_KEY = intPreferencesKey("USER_AGE")
        val USER_NAME_KEY = stringPreferencesKey("USER_NAME")
        val USER_GENDER_KEY = booleanPreferencesKey("USER_GENDER")
        @Volatile
        private var INSTANCE: UserManager? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserManager {
            return INSTANCE ?: synchronized(this) {
                val instance = UserManager(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    //Store user data
    suspend fun storeUser(age: Int, name: String, isMale: Boolean) {
        dataStore.edit {
            it[USER_AGE_KEY] = age
            it[USER_NAME_KEY] = name
            it[USER_GENDER_KEY] = isMale
        }
    }

    //Get and Create an age flow
    fun userAgeFlow(): Flow<Int> {
        return dataStore.data.map {
            it[USER_AGE_KEY] ?: 0
        }
    }

    //Get and Create a name flow
    fun userNameFlow(): Flow<String> {
        return dataStore.data.map {
            it[USER_NAME_KEY] ?: ""
        }
    }

    //Get and Create a gender flow
    fun userGenderFlow(): Flow<Boolean> {
        return dataStore.data.map {
            it[USER_GENDER_KEY] ?: false
        }
    }
}