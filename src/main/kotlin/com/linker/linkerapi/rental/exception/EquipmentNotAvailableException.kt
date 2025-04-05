package com.linker.linkerapi.rental.exception

import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(
    code = org.springframework.http.HttpStatus.NOT_FOUND,
    reason = "해당 기자재는 대여할 수 없습니다."
)
class EquipmentNotAvailableException : Exception()