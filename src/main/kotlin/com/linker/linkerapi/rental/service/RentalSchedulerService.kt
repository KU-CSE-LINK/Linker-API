package com.linker.linkerapi.rental.service

import com.linker.linkerapi.notification.service.SmsService
import com.linker.linkerapi.rental.enums.RentalStatus
import com.linker.linkerapi.rental.repository.RentalRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class RentalSchedulerService(
    private val rentalRepository: RentalRepository,
    private val smsService: SmsService
) {
    private val logger = LoggerFactory.getLogger(RentalSchedulerService::class.java)

    @Scheduled(cron = "0 0 9 * * ?")
    fun sendReturnDateReminders() {
        val today = LocalDate.now()
        val startOfDay = today.atStartOfDay()
        val endOfDay = today.plusDays(1).atStartOfDay().minusNanos(1)

        val returnDueRentals = rentalRepository.findAllByReturnDateBetweenAndStatus(
            startOfDay,
            endOfDay,
            RentalStatus.RENTED
        )

        returnDueRentals.forEach { rental ->
            try {
                smsService.sendReturnDateReminder(rental)
                logger.info("반납일 알림 문자 발송 성공 - 대여ID: ${rental.id}, 이름: ${rental.name}, 기자재: ${rental.equipment.name}")
            } catch (e: Exception) {
                logger.error("반납일 알림 문자 발송 실패 - 대여ID: ${rental.id}, 이름: ${rental.name}, 오류: ${e.message}", e)
            }
        }
    }
}