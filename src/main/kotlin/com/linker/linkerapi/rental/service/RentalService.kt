package com.linker.linkerapi.rental.service

import com.linker.linkerapi.rental.entity.Rental
import com.linker.linkerapi.rental.repository.RentalRepository
import org.springframework.stereotype.Service

@Service
class RentalService(
    private val rentalRepository: RentalRepository
) {
    fun createRental(rental: Rental): Rental {
        rental.status = "신청"
        return rentalRepository.save(rental)
    }
}
