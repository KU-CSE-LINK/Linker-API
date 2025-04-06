package com.linker.linkerapi.rental.repository

import com.linker.linkerapi.rental.entity.Rental
import com.linker.linkerapi.rental.enums.RentalStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface RentalRepository : JpaRepository<Rental, Long> {
    fun findAllByStudentIdAndName(
        studentId: Long,
        name: String
    ): List<Rental>

    fun findAllByReturnDateBetweenAndStatus(
        startOfDay: LocalDateTime,
        endOfDay: LocalDateTime,
        status: RentalStatus
    ): List<Rental>
}