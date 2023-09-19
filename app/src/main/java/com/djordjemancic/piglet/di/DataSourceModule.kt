package com.djordjemancic.piglet.di

import com.djordjemancic.piglet.data.local.LocalDataSource
import com.djordjemancic.piglet.data.local.dao.PigletUserDao
import com.djordjemancic.piglet.data.local.dao.TagDao
import com.djordjemancic.piglet.data.local.dao.TransactionDao
import com.djordjemancic.piglet.data.remote.RemoteDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideLocalDataSource(userDao: PigletUserDao, tagDao: TagDao, transactionDao: TransactionDao): LocalDataSource {
        return LocalDataSource(userDao, tagDao, transactionDao)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(firestore: FirebaseFirestore, auth: FirebaseAuth, gson: Gson): RemoteDataSource {
        return RemoteDataSource(firestore, auth, gson)
    }
}