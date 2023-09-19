package com.djordjemancic.piglet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.djordjemancic.piglet.data.local.entities.PigletUserEntity
import com.djordjemancic.piglet.data.local.entities.UserDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PigletUserDao {
    @Upsert
    suspend fun upsertUser(user: UserDataEntity)

    @Delete
    suspend fun deleteUser(user: UserDataEntity)

    @Transaction
    @Query("SELECT * FROM UserDataEntity WHERE userId = (:userId)")
    fun getUser(userId: String): Flow<PigletUserEntity>
}