package com.linker.linkerapi.rental.service

import com.linker.linkerapi.equipment.service.EquipmentService
import com.linker.linkerapi.notification.service.DiscordNotificationService
import com.linker.linkerapi.notification.service.SmsService
import com.linker.linkerapi.rental.entity.Rental
import com.linker.linkerapi.rental.enums.RentalStatus
import com.linker.linkerapi.rental.enums.RentalType
import com.linker.linkerapi.rental.exception.RentalNotFoundException
import com.linker.linkerapi.rental.repository.RentalRepository
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

    fun getAllRentals(): List<Rental> {
        return rentalRepository.findAll()
    }

    fun changeRentalStatus(id: Long, rawStatus: String): Rental {
        val rental = rentalRepository.findById(id).get()
        val currentStatus = rental.status
        val newStatus = RentalStatus.valueOf(rawStatus)

        if (currentStatus == newStatus) {
            return rental
        }

        when {
            (currentStatus == RentalStatus.REQUESTED || currentStatus == RentalStatus.PREPARED) &&
                    newStatus == RentalStatus.RENTED -> {
                val equipment = rental.equipment
                if (equipment.availableStock > 0) {
                    equipment.availableStock -= 1
                    equipmentService.updateEquipment(equipment)
                } else {
                    logger.error("기자재 재고 부족: ${equipment.name}, ID: ${equipment.id}")
                    throw IllegalStateException("기자재 재고가 부족합니다: ${equipment.name}")
                }
            }

            currentStatus == RentalStatus.RENTED && newStatus == RentalStatus.RETURNED -> {
                val equipment = rental.equipment
                equipment.availableStock += 1
                equipmentService.updateEquipment(equipment)
            }

            else -> {
                if (currentStatus == RentalStatus.RETURNED && newStatus == RentalStatus.RENTED) {
                    throw IllegalStateException("이미 반납된 기자재는 다시 대여 상태로 변경할 수 없습니다.")
                }
            }
        }

        rental.status = newStatus
        val savedRental = rentalRepository.save(rental)

        try {
            smsService.sendRentalStatusNotification(savedRental)
        } catch (e: Exception) {
            logger.error(
                "대여 상태 변경 SMS 발송 실패 - 대여ID: ${savedRental.id}, 상태: ${newStatus}, 오류: ${e.message}",
                e
            )
        }

        return savedRental
    }
}