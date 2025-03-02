package com.linker.linkerapi.rental.repository

import com.linker.linkerapi.rental.entity.Rental
import org.springframework.data.jpa.repository.JpaRepository

interface RentalRepository : JpaRepository<Rental, Long> {
    fun findAllByStudentIdAndName(
        studentId: Long,
        name: String
    ): List<Rental>
}