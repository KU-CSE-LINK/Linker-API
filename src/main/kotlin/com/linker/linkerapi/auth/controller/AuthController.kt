package com.linker.linkerapi.auth.controller

import com.linker.linkerapi.auth.dto.LoginRequestBody
import com.linker.linkerapi.auth.service.AuthService
import com.linker.linkerapi.security.JwtProvider
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.web.server.Cookie.SameSite
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService,
    private val jwtProvider: JwtProvider
) {
    @Operation(summary = "로그인", description = "로그인을 처리합니다.")
    @PostMapping("/login")
    @ResponseBody
    fun login(
        @RequestBody body: LoginRequestBody,
        response: HttpServletResponse
    ): ResponseEntity<String> {
        authService.login(body.code)

        val token = jwtProvider.createToken()
        val authCookie =
            ResponseCookie.from(HttpHeaders.AUTHORIZATION, token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite(SameSite.NONE.name)
                .build()

        response.addHeader(HttpHeaders.SET_COOKIE, authCookie.toString())
        return ResponseEntity.ok().body("login success")
    }

    @Operation(summary = "로그인 url 전송", description = "디스코드로 로그인 url을 전송합니다.")
    @PostMapping("/login/url")
    fun sendLoginUrl() {
        authService.sendLoginUrl()
    }
}