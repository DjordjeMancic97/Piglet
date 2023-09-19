package com.djordjemancic.piglet.di

import com.djordjemancic.piglet.data.Repository
import com.djordjemancic.piglet.data.local.LocalDataSource
import com.djordjemancic.piglet.data.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource): Repository {
        return Repository(localDataSource, remoteDataSource)
    }
}