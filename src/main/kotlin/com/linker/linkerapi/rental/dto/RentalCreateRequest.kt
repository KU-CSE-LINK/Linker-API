package com.linker.linkerapi.rental.dto

data class RentalCreateRequest(
    val equipmentId: Long,
    val phoneNumber: String,
    val name: String,
    val studentId: Long,
    val rentalType: String
)