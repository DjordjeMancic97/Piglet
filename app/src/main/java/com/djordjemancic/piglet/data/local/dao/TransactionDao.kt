package com.djordjemancic.piglet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.djordjemancic.piglet.data.local.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Upsert
    suspend fun upsertTransaction(transactionEntity: List<TransactionEntity>)

    @Delete
    suspend fun deleteTransaction(transactionEntity: TransactionEntity)

    @Transaction
    @Query("SELECT * FROM TransactionEntity WHERE ownerId = (:userId)")
    fun getTransactions(userId: String): Flow<List<TransactionEntity>>
}