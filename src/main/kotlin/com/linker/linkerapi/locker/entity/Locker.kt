package com.linker.linkerapi.locker.entity

import com.linker.linkerapi.common.entity.BaseEntity
import com.linker.linkerapi.locker.enums.LockerStatus
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class Locker(
    val lockerName: String,
    val location: String,
    @Enumerated(EnumType.STRING)
    var status: LockerStatus = LockerStatus.AVAILABLE,
    var studentId: Long? = null,
    var userName: String? = null,
    var phoneNumber: String? = null
) : BaseEntity()