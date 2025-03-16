package com.linker.linkerapi.rental.service

import com.linker.linkerapi.equipment.service.EquipmentService
import com.linker.linkerapi.notification.service.DiscordNotificationService
import com.linker.linkerapi.rental.entity.Rental
import com.linker.linkerapi.rental.enums.RentalType
import com.linker.linkerapi.rental.repository.RentalRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class RentalService(
    private val rentalRepository: RentalRepository,
    private val equipmentService: EquipmentService,
    private val discordNotificationService: DiscordNotificationService
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
        val rentalPeriod = if (rentalType == RentalType.SHORT_TERM) 7 else 30
        val returnDate = LocalDate.now().plusDays(rentalPeriod.toLong()).atStartOfDay()

        val savedRental = rentalRepository.save(
            Rental(
                equipment = equipment,
                phoneNumber = phoneNumber,
                name = name,
                studentId = studentId,
                returnDate = returnDate,
                rentalType = rentalType
            )
        )
        discordNotificationService.sendRentalRequestNotification(savedRental)

        return savedRental
    }

    fun getRentalsByUserInfo(name: String, studentId: Long): List<Rental> {
        return rentalRepository.findAllByStudentIdAndName(studentId, name)
    }

    fun getRentals(page: Int, size: Int): Page<Rental> {
        return rentalRepository.findAll(PageRequest.of(page, size))
    }
}