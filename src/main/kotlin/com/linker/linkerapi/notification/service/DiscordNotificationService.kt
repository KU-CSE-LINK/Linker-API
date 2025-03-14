package com.linker.linkerapi.notification.service

import com.linker.linkerapi.notification.api.DiscordWebhookApi
import com.linker.linkerapi.notification.dto.DiscordEmbed
import com.linker.linkerapi.notification.dto.DiscordEmbedField
import com.linker.linkerapi.notification.dto.DiscordWebhookDto
import com.linker.linkerapi.rental.entity.Rental
import com.linker.linkerapi.rental.enums.RentalType
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class DiscordNotificationService(
    @Value("\${retrofit.discord.webhook-url}")
    private val webhookUrl: String,
    private val discordWebhookApi: DiscordWebhookApi
) {
    fun sendRentalRequestNotification(rental: Rental) {
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
                value = when (rental.rentalType) {
                    RentalType.SHORT_TERM -> "단기 대여"
                    RentalType.LONG_TERM -> "장기 대여"
                    else -> rental.rentalType.name
                }
            )
        )

        val embeds = listOf(
            DiscordEmbed(
                title = "🔔 새로운 기자재 대여 신청",
                description = "${rental.name}님이 ${rental.equipment.name}을(를) 대여 신청했습니다.",
                color = 0x00aaff,
                fields = fields
            )
        )

        val webhookRequest = DiscordWebhookDto(
            username = "Linker 대여 알림",
            embeds = embeds
        )

        discordWebhookApi.executeWebhook(webhookUrl, webhookRequest).execute()
    }

    fun sendLoginUrlNotification(code: String) {
        // todo change url after implementing login
        val url = code

        val fields = listOf(
            DiscordEmbedField(
                name = "🔗 로그인 링크",
                value = url
            )
        )

        val embeds = listOf(
            DiscordEmbed(
                title = "🔔 새로운 로그인 링크",
                description = "새로운 로그인 링크가 발급되었습니다.",
                color = 0x00aaff,
                fields = fields
            )
        )

        val webhookRequest = DiscordWebhookDto(
            username = "Linker 로그인 알림",
            embeds = embeds
        )

        discordWebhookApi.executeWebhook(webhookUrl, webhookRequest).execute()
    }
}