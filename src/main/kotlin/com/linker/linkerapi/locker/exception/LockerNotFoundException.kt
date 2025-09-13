package com.linker.linkerapi.locker.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "사물함 정보를 찾을 수 없습니다.")
class LockerNotFoundException : RuntimeException()