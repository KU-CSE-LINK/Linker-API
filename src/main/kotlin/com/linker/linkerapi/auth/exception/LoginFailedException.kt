package com.linker.linkerapi.auth.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "로그인을 실패했습니다.")
class LoginFailedException : Exception()