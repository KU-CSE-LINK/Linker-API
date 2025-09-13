package com.linker.linkerapi.locker.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "이미 사물함을 대여했거나, 해당 사물함은 대여할 수 없는 상태입니다.")
class LockerAlreadyRentedException : RuntimeException()