package com.linker.linkerapi.rental.dto

import com.linker.linkerapi.rental.enums.RentalType

data class RentalCreateRequest(
    val equipmentId: Long,
    val phoneNumber: String,
    val name: String,
    val studentId: Int,
    val rentalType: RentalType
)
