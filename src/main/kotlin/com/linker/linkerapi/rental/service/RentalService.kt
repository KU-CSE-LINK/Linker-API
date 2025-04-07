package com.linker.linkerapi.rental.service

import com.linker.linkerapi.equipment.service.EquipmentService
import com.linker.linkerapi.notification.service.DiscordNotificationService
import com.linker.linkerapi.notification.service.SmsService
import com.linker.linkerapi.rental.entity.Rental
import com.linker.linkerapi.rental.enums.RentalStatus
import com.linker.linkerapi.rental.enums.RentalType
import com.linker.linkerapi.rental.exception.EquipmentNotAvailableException
import com.linker.linkerapi.rental.exception.RentalNotFoundException
import com.linker.linkerapi.rental.repository.RentalRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class RentalService(
    private val rentalRepository: RentalRepository,
    private val equipmentService: EquipmentService,
    private val discordNotificationService: DiscordNotificationService,
    private val smsService: SmsService
) {
    private val logger = LoggerFactory.getLogger(RentalService::class.java)

    @Transactional
    fun createRental(
        equipmentId: Long,
        phoneNumber: String,
        name: String,
        studentId: Long,
        rawRentalType: String
    ): Rental {
        val equipment = equipmentService.getEquipmentById(equipmentId)
        if (equipment.availableStock == 0) throw EquipmentNotAvailableException()

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
        equipment.availableStock -= 1
        equipmentService.saveEquipment(equipment)

        discordNotificationService.sendRentalRequestNotification(savedRental)

        try {
            smsService.sendRentalStatusNotification(savedRental)
        } catch (e: Exception) {
            logger.error("대여 신청 확인 SMS 발송 실패: ${e.message}", e)
        }

        return savedRental
    }

    fun getRentalById(id: Long): Rental {
        return rentalRepository.findById(id)
            .orElseThrow { RentalNotFoundException() }
    }

    fun getRentalsByUserInfo(name: String, studentId: Long): List<Rental> {
        return rentalRepository.findAllByStudentIdAndName(studentId, name)
    }

    fun getAllRentals(status: String?): List<Rental> {
        if (status != null) {
            return rentalRepository.findAllByStatusOrderByReturnDate(RentalStatus.valueOf(status))
        }

        return rentalRepository.findAllByOrderByReturnDate()
    }

    @Transactional
    fun changeRentalStatus(id: Long, rawStatus: String): Rental {
        val rental = rentalRepository.findById(id).get()
        val previousStatus = rental.status
        val currentStatus = RentalStatus.valueOf(rawStatus)

        if (rental.status != currentStatus) {
            val equipmentStockChange =
                if (previousStatus == RentalStatus.RETURNED) -1
                else if (currentStatus == RentalStatus.RETURNED) 1
                else 0

            rental.status = currentStatus
            rental.equipment.availableStock += equipmentStockChange
            val savedRental = rentalRepository.save(rental)

            try {
                smsService.sendRentalStatusNotification(savedRental)
            } catch (e: Exception) {
                logger.error(
                    "대여 상태 변경 SMS 발송 실패 - 대여ID: ${savedRental.id}, 상태: ${currentStatus}, 오류: ${e.message}",
                    e
                )
            }

            return savedRental
        }

        return rental
    }
}