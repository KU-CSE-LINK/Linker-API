package com.linker.linkerapi.locker.dto

import com.linker.linkerapi.locker.enums.LockerStatus

data class UpdateLockerStatusRequest(
    val status: LockerStatus
)