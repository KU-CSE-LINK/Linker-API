package com.linker.linkerapi.locker.dto

data class RentLockerRequest(
    val studentId: Long,
    val userName: String,
    val phoneNumber: String
)