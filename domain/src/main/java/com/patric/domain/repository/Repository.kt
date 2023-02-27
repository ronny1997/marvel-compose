package com.patric.domain.repository

interface Repository {
    suspend fun login(dto: String)
}