package com.linker.linkerapi.rental.dto

import com.linker.linkerapi.rental.entity.Rental
import com.linker.linkerapi.rental.enums.RentalStatus
import com.linker.linkerapi.rental.enums.RentalType
import java.time.LocalDateTime

data class RentalResponse(
    val id: Long,
    val equipmentName: String,
    val phoneNumber: String,
    val name: String,
    val studentId: Long,
    val rentalType: RentalType,
    val status: RentalStatus,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(rental: Rental): RentalResponse {
            return RentalResponse(
                id = rental.id,
                equipmentName = rental.equipment.name,
                phoneNumber = rental.phoneNumber,
                name = rental.name,
                studentId = rental.studentId,
                rentalType = rental.rentalType,
                status = rental.status,
                createdAt = rental.createdAt,
                updatedAt = rental.updatedAt
            )
        }
    }
}