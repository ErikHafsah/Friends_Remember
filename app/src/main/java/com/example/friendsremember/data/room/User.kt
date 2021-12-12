package com.example.friendsremember.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nama: String,
    val gender: String,
    val alamat: String,
    val dueDateMillis: Long,
    val imagePhoto: Int = 0,
    val isCompleted: Boolean = false
)
