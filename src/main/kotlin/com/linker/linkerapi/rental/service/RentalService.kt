package com.linker.linkerapi.rental.service

import com.linker.linkerapi.equipment.service.EquipmentService
import com.linker.linkerapi.rental.entity.Rental
import com.linker.linkerapi.rental.enums.RentalType
import com.linker.linkerapi.rental.repository.RentalRepository
import org.springframework.stereotype.Service

@Service
class RentalService(
    private val rentalRepository: RentalRepository,
    private val equipmentService: EquipmentService
) {
    fun createRental(
        equipmentId: Long,
        phoneNumber: String,
        name: String,
        studentId: Long,
        rentalType: RentalType
    ): Rental {
        val equipment = equipmentService.getEquipmentById(equipmentId)
        val rental = Rental(
            equipment = equipment,
            phoneNumber = phoneNumber,
            name = name,
            studentId = studentId,
            rentalType = rentalType
        )
        return rentalRepository.save(rental)
    }
}
