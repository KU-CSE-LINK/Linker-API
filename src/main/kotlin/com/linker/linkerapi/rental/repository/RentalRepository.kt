package com.linker.linkerapi.rental.repository

import com.linker.linkerapi.rental.entity.Rental
import org.springframework.data.jpa.repository.JpaRepository

interface RentalRepository : JpaRepository<Rental, Long> {
    fun findByStudentIdAndPhoneNumberAndName(
        studentId: Long,
        phoneNumber: String,
        name: String
    ): List<Rental>
}