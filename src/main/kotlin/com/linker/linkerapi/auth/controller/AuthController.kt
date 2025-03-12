package com.linker.linkerapi.auth.controller

import com.linker.linkerapi.auth.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService
) {
    @Operation(summary = "로그인", description = "로그인을 처리합니다.")
    @PostMapping("/login")
    fun login() {
        authService.login()
    }
}