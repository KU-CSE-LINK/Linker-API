package com.linker.linkerapi.notification.service

import com.linker.linkerapi.notification.api.DiscordWebhookService
import com.linker.linkerapi.notification.dto.DiscordEmbed
import com.linker.linkerapi.notification.dto.DiscordEmbedField
import com.linker.linkerapi.notification.dto.DiscordWebhookRequest
import com.linker.linkerapi.rental.entity.Rental
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class DiscordNotificationService(
    private val discordWebhookService: DiscordWebhookService
) {
    private val logger = LoggerFactory.getLogger(DiscordNotificationService::class.java)

    @Value("\${notification.discord.webhook-url}")
    private lateinit var webhookUrl: String

    fun sendRentalRequestNotification(rental: Rental) {
        try {
            val fields = listOf(
                DiscordEmbedField(
                    name = "🧑‍🎓 신청자 정보",
                    value = "이름: ${rental.name}\n학번: ${rental.studentId}\n연락처: ${rental.phoneNumber}"
                ),
                DiscordEmbedField(
                    name = "📦 대여 물품",
                    value = rental.equipment.name
                ),
                DiscordEmbedField(
                    name = "⏱️ 대여 타입",
                    value = when (rental.rentalType.name) {
                        "SHORT_TERM" -> "단기 대여"
                        "LONG_TERM" -> "장기 대여"
                        else -> rental.rentalType.name
                    }
                )
            )

            val embed = DiscordEmbed(
                title = "🔔 새로운 기자재 대여 신청",
                description = "${rental.name}님이 ${rental.equipment.name}을(를) 대여 신청했습니다.",
                color = 0x00aaff,
                fields = fields
            )

            val webhookRequest = DiscordWebhookRequest(
                username = "Linker 대여 알림",
                embeds = listOf(embed)
            )

            val response = discordWebhookService.executeWebhook(webhookUrl, webhookRequest).execute()

            if (response.isSuccessful) {
                logger.info("Discord 알림 전송 성공: ${rental.name}님의 대여 신청")
            } else {
                logger.error("Discord 알림 전송 실패 - HTTP 상태 코드: ${response.code()}")
            }
        } catch (e: Exception) {
            logger.error("Discord 알림 전송 중 오류 발생", e)
        }
    }
}