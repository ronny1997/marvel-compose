package com.mpapps.marvelcompose.framework.datasources.cloud

abstract class CloudDataSourceImpl {

    companion object {
        private const val ERROR_404 = 404
    }

    protected suspend inline fun <reified T> execute(call: () -> T): T {
        try {
            return call()
        } catch (ex: Exception) {
            throw DataNotFoundException("")
        }
    }


}

data class DataNotFoundException(override val message: String) : DataException(message)
open class DataException(message: String? = null, open var code: String = "") :
    RuntimeException(message) {
    override fun toString(): String {
        return "DataException(code='$code', message='$message)"
    }
}
