package com.linker.linkerapi.rental.dto

data class RentalQueryRequest(
    val studentId: Long,
    val phoneNumber: String,
    val name: String
) {
    fun isValid(): Boolean {
        return studentId > 0 && phoneNumber.isNotBlank() && name.isNotBlank()
    }
}