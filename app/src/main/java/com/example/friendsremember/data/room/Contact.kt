package com.example.friendsremember.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
class Contact(
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "Surname") val surname: String,
    @ColumnInfo(name = "PhoneNumber") val phoneNumber: Int,
    @ColumnInfo(name = "Email") val email: String,
    @ColumnInfo(name = "Gender") val gender: String
//    @ColumnInfo(name = "Image", typeAffinity = ColumnInfo.BLOB) val image: Byte
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var id: Int = 0
}