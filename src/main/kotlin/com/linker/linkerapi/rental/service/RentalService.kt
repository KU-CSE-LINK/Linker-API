package com.linker.linkerapi.rental.service

import com.linker.linkerapi.equipment.service.EquipmentService
import com.linker.linkerapi.notification.service.NotificationService
import com.linker.linkerapi.rental.entity.Rental
import com.linker.linkerapi.rental.enums.RentalType
import com.linker.linkerapi.rental.repository.RentalRepository
import org.springframework.stereotype.Service

@Service
class RentalService(
    private val rentalRepository: RentalRepository,
    private val equipmentService: EquipmentService,
    private val notificationService: NotificationService
) {
    fun createRental(
        equipmentId: Long,
        phoneNumber: String,
        name: String,
        studentId: Long,
        rawRentalType: String
    ): Rental {
        val equipment = equipmentService.getEquipmentById(equipmentId)
        val rentalType = RentalType.valueOf(rawRentalType)
        val rental = Rental(
            equipment = equipment,
            phoneNumber = phoneNumber,
            name = name,
            studentId = studentId,
            rentalType = rentalType
        )
        val savedRental = rentalRepository.save(rental)

        notificationService.sendRentalNotification(savedRental)

        return savedRental
    }

    fun getRentalsByUserInfo(name: String, studentId: Long): List<Rental> {
        return rentalRepository.findAllByStudentIdAndName(studentId, name)
    }
}