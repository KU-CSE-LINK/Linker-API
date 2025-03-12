package com.linker.linkerapi.notification.controller

import com.linker.linkerapi.equipment.entity.Equipment
import com.linker.linkerapi.notification.service.DiscordNotificationService
import com.linker.linkerapi.rental.entity.Rental
import com.linker.linkerapi.rental.enums.RentalStatus
import com.linker.linkerapi.rental.enums.RentalType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.context.annotation.Profile
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "알림 테스트 컨트롤러")
@RestController
@RequestMapping("/api/test")
@Profile("dev", "local", "default")
class NotificationTestController(
    private val discordNotificationService: DiscordNotificationService
) {

    @Operation(summary = "Discord 알림 테스트", description = "Discord 웹훅 알림이 정상적으로 작동하는지 테스트합니다.")
    @PostMapping("/discord-notification")
    fun testDiscordNotification(): ResponseEntity<String> {

        val testEquipment = Equipment(
            name = "테스트 기자재",
            totalStock = 10,
            availableStock = 5
        )

        val testRental = Rental(
            equipment = testEquipment,
            phoneNumber = "010-1234-5678",
            name = "테스트 사용자",
            studentId = 20201234,
            rentalType = RentalType.SHORT_TERM,
            status = RentalStatus.REQUESTED
        )

        discordNotificationService.sendRentalRequestNotification(testRental)

        return ResponseEntity.ok("Discord 알림이 전송되었습니다. Discord 채널을 확인해주세요.")
    }
}