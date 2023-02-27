package com.patric.core.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("error")
    val errorMsg: String
) {
    companion object {
        private const val TENANT_NOT_FOUND = "TENANT_NOT_FOUND"
        private const val USERNAME_AND_PASSWORD_DO_NOT_MATCH = "USERNAME_AND_PASSWORD_DO_NOT_MATCH"
        private const val WORKFLOW_USED_IN_ANOTHER_SESSION = "WorkflowApplicationExceptionsDeviceUsedInOtherSessionBySameUserException"

        fun transformToError(txt: String): String = if (!txt.contains("{")) {
            "{ error: $txt }"
        } else {
            txt
        }
    }

    fun messageError(): String = when (errorMsg) {
        TENANT_NOT_FOUND -> "Cuenta no encontrado"
        USERNAME_AND_PASSWORD_DO_NOT_MATCH -> "Usuario o contraseña incorrectos"
        WORKFLOW_USED_IN_ANOTHER_SESSION -> "Flujo de trabajo utilizado en otra sesión"
        else -> "Ha habido un error, por favor inténtalo más tarde"
    }
}
