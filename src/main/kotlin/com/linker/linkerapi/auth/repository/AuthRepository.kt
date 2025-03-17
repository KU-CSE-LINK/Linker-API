package com.linker.linkerapi.auth.repository

import com.linker.linkerapi.auth.entity.Auth
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthRepository : JpaRepository<Auth, Long> {
    fun findByCode(code: String): Auth?
}