package com.mpapps.marvelcompose.data.infrastructure.di

import com.mpapps.marvelcompose.data.MarvelRepositoryImpl
import com.mpapps.marvelcompose.data.remote.MarvelDataSource
import com.mpapps.marvelcompose.domain.repository.MarvelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideMarvelRepository(
        marvelDataSource: MarvelDataSource
    ): MarvelRepository = MarvelRepositoryImpl(marvelDataSource)
}
