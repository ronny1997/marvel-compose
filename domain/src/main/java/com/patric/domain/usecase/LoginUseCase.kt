package com.patric.domain.usecase

import com.patric.core.domain.UseCase
import com.patric.core.settings.SettingsApp
import com.patric.domain.repository.Repository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: Repository,
    private val settingsApp: SettingsApp
) : UseCase<Unit, String>() {

    override suspend fun run(params: String) {
        val response = repository.login(params)
        with(settingsApp) {
            set(SettingsApp.TOKEN,"")
        }
    }
}