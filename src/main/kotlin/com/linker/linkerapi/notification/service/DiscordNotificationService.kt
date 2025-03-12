package com.linker.linkerapi.notification.service

import com.linker.linkerapi.notification.api.DiscordWebhookApi
import com.linker.linkerapi.notification.dto.DiscordEmbed
import com.linker.linkerapi.notification.dto.DiscordEmbedField
import com.linker.linkerapi.notification.dto.DiscordWebhookDto
import com.linker.linkerapi.rental.entity.Rental
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class DiscordNotificationService(
    private val discordWebhookApi: DiscordWebhookApi
) {
    @Value("\${notification.discord.webhook-url}")
    private lateinit var webhookUrl: String

    /**
     * 대여 신청이 발생했을 때 Discord로 알림을 보냅니다.
     * @param rental 대여 신청 정보
     */
    fun sendRentalRequestNotification(rental: Rental) {
        // 디스코드 임베드 필드 생성
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

        // 임베드 객체 리스트 생성
        val embeds = listOf(
            DiscordEmbed(
                title = "🔔 새로운 기자재 대여 신청",
                description = "${rental.name}님이 ${rental.equipment.name}을(를) 대여 신청했습니다.",
                color = 0x00aaff,  // 파란색
                fields = fields
            )
        )

        // 웹훅 요청 객체 생성
        val webhookRequest = DiscordWebhookDto(
            username = "Linker 대여 알림",
            embeds = embeds
        )

        // 웹훅 요청 실행
        discordWebhookApi.executeWebhook(webhookUrl, webhookRequest).execute()
    }
}