package com.djordjemancic.piglet.di

import android.content.Context
import androidx.room.Room
import com.djordjemancic.piglet.data.local.dao.PigletDatabase
import com.djordjemancic.piglet.data.local.dao.PigletUserDao
import com.djordjemancic.piglet.data.local.dao.TagDao
import com.djordjemancic.piglet.data.local.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideUserDao(db: PigletDatabase): PigletUserDao {
        return db.userDao
    }

    @Provides
    @Singleton
    fun provideTagDao(db: PigletDatabase): TagDao {
        return db.tagDao
    }

    @Provides
    @Singleton
    fun provideTransactionDao(db: PigletDatabase): TransactionDao {
        return db.transactionDao
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): PigletDatabase {
        return Room.databaseBuilder(appContext, PigletDatabase::class.java,  "piglet_database").build()
    }
}