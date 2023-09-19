package com.djordjemancic.piglet.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.djordjemancic.piglet.data.local.entities.UserDataEntity
import com.djordjemancic.piglet.data.local.entities.TagEntity
import com.djordjemancic.piglet.data.local.entities.TransactionEntity

@Database(
    entities = [UserDataEntity::class, TagEntity::class, TransactionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PigletDatabase: RoomDatabase() {
    abstract val userDao: PigletUserDao
    abstract val tagDao: TagDao
    abstract val transactionDao: TransactionDao
}