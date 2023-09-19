package com.djordjemancic.piglet.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity()
data class UserDataEntity(
    @PrimaryKey
    val userId: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
)