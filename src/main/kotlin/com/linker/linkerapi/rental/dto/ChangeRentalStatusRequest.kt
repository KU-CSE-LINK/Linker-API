package com.linker.linkerapi.rental.dto

data class ChangeRentalStatusRequest(
    val id: Long,
    val status: String
)