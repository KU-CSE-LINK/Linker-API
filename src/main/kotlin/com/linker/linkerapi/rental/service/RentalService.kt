package com.linker.linkerapi.rental.service

import com.linker.linkerapi.equipment.repository.EquipmentRepository
import com.linker.linkerapi.rental.dto.RentalCreateRequest
import com.linker.linkerapi.rental.entity.Rental
import com.linker.linkerapi.rental.repository.RentalRepository
import org.springframework.stereotype.Service

@Service
class RentalService(
    private val rentalRepository: RentalRepository,
    private val equipmentRepository: EquipmentRepository
) {
    fun createRental(request: RentalCreateRequest): Rental {
        val equipment = equipmentRepository.findById(request.equipmentId)
            .orElseThrow { RuntimeException("Equipment not found with id: ${request.equipmentId}") }

        val rental = Rental(
            equipment = equipment,
            phoneNumber = request.phoneNumber,
            name = request.name,
            studentId = request.studentId,
            rentalType = request.rentalType
        )
        return rentalRepository.save(rental)
    }
}
