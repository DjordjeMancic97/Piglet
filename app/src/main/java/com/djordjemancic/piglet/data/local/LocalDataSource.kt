package com.djordjemancic.piglet.data.local

import com.djordjemancic.piglet.data.local.dao.PigletUserDao
import com.djordjemancic.piglet.data.local.dao.TagDao
import com.djordjemancic.piglet.data.local.dao.TransactionDao
import com.djordjemancic.piglet.data.local.entities.PigletUserEntity
import com.djordjemancic.piglet.data.local.entities.TransactionEntity
import com.djordjemancic.piglet.data.local.entities.UserDataEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val userDao: PigletUserDao,
    private val tagDao: TagDao,
    private val transactionDao: TransactionDao,
) {
    suspend fun createOrUpdateUser(user: PigletUserEntity) {
        userDao.upsertUser(user.userData)
        tagDao.upsertTag(user.tags)
        transactionDao.upsertTransaction(user.transactions)
    }

    suspend fun deleteUser(user: UserDataEntity) = userDao.deleteUser(user)

    fun getUser(userId: String): Flow<PigletUserEntity> =
       userDao.getUser(userId)

    suspend fun addOrUpdateTransaction(transaction: TransactionEntity) = transactionDao.upsertTransaction(
        listOf<TransactionEntity>(transaction)
    )

}