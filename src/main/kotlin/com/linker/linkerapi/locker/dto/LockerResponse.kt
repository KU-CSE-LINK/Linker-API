package com.linker.linkerapi.locker.dto

import com.linker.linkerapi.locker.entity.Locker
import com.linker.linkerapi.locker.enums.LockerStatus

data class LockerResponse(
    val id: Long,
    val lockerName: String,
    val location: String,
    val status: LockerStatus,
    val studentId: Long?
) {
    companion object {
        fun from(locker: Locker) = LockerResponse(
            id = locker.id,
            lockerName = locker.lockerName,
            location = locker.location,
            status = locker.status,
            studentId = locker.studentId
        )
    }
}