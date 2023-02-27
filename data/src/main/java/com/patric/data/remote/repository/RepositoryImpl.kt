package com.patric.data.remote.repository

import com.patric.core.settings.SettingsApp
import com.patric.data.remote.datasource.api.Api
import com.patric.data.remote.repository.mapper.toDomain
import com.patric.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: Api,
    private val settingsApp: SettingsApp
) : Repository {

    override suspend fun login(dto: String){
         api.login("").toDomain()
    }
}