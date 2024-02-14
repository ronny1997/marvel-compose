package com.mpapps.marvelcompose.data.infrastructure.di

import com.mpapps.marvelcompose.data.MarvelRepositoryImpl
import com.mpapps.marvelcompose.domain.repository.MarvelRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {
    @Binds
    abstract fun provideMarvelRepository(
        marvelRepository: MarvelRepositoryImpl
    ): MarvelRepository
}
