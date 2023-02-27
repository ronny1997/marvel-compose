package com.patric.skeleton.di

import com.patric.core.utils.ErrorApp
import com.patric.core.utils.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Provides
    @Singleton
    fun providerNavigation(): Navigator = Navigator()

    @Provides
    @Singleton
    fun providerErrorApp(): ErrorApp = ErrorApp()

}