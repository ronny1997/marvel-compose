package com.patric.skeleton.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.patric.core.settings.SettingsApp
import com.patric.core.settings.SettingsAppImpl
import com.patric.data.remote.datasource.api.Api
import com.patric.data.remote.repository.RepositoryImpl
import com.patric.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.patric.core.data.KtorApiClient
import com.patric.core.utils.Constants
import com.patric.data.local.AppDatabase

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    @Provides
    @Singleton
    fun providerRepository(api: Api, settingsApp: SettingsApp): Repository =
        RepositoryImpl(api, settingsApp)


    @Provides
    @Singleton
    fun providerApi(ktorApiClient: KtorApiClient): Api =
        Api(ktorApiClient)

    @Provides
    @Singleton
    fun providerDataStored(@ApplicationContext appContext: Context): SettingsApp =
        SettingsAppImpl(appContext.dataStore)


    @Provides
    @Singleton
    fun providerRoom(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, Constants.DATA_BASE_NAME
        ).build()

}