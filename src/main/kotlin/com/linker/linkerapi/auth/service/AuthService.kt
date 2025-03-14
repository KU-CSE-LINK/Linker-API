package com.linker.linkerapi.auth.service

import com.linker.linkerapi.auth.entity.Auth
import com.linker.linkerapi.auth.exception.LoginFailedException
import com.linker.linkerapi.auth.repository.AuthRepository
import com.linker.linkerapi.notification.service.DiscordNotificationService
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    private val authRepository: AuthRepository,
    private val discordNotificationService: DiscordNotificationService
) {
    fun login(code: String) {
        authRepository.findByCode(code) ?: throw LoginFailedException()
    }

    fun sendLoginUrl() {
        authRepository.deleteAll()

        val code = UUID.randomUUID().toString()
        val auth = authRepository.save(Auth(code = code))
        discordNotificationService.sendLoginUrlNotification(auth.code)
    }
}