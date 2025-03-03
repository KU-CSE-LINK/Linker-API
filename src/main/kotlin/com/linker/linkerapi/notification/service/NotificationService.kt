package com.linker.linkerapi.notification.service

import com.linker.linkerapi.notification.client.DiscordWebhookClient
import com.linker.linkerapi.notification.dto.DiscordEmbed
import com.linker.linkerapi.notification.dto.DiscordField
import com.linker.linkerapi.notification.dto.DiscordWebhookRequest
import com.linker.linkerapi.rental.entity.Rental
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Service
class NotificationService(
    private val discordWebhookClient: DiscordWebhookClient?
) {
    private val logger = LoggerFactory.getLogger(NotificationService::class.java)

    fun sendRentalNotification(rental: Rental) {
        if (discordWebhookClient == null) {
            logger.info("디스코드 웹훅 URL이 설정되지 않아 알림을 보내지 않습니다.")
            return
        }

        val fields = listOf(
            DiscordField("신청자", rental.name),
            DiscordField("학번", rental.studentId.toString()),
            DiscordField("전화번호", rental.phoneNumber),
            DiscordField("기자재", rental.equipment.name),
            DiscordField("대여 유형", rental.rentalType.name)
        )

        val embed = DiscordEmbed(
            title = "새로운 기자재 대여 신청",
            description = "새로운 기자재 대여 신청이 있습니다.",
            fields = fields
        )

        val request = DiscordWebhookRequest(
            content = "새로운 대여 신청: ${rental.name}님이 ${rental.equipment.name}을(를) 신청했습니다.",
            embeds = listOf(embed)
        )

        discordWebhookClient.sendMessage(request).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    logger.info("디스코드 알림 전송 성공")
                } else {
                    logger.error("디스코드 알림 전송 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                logger.error("디스코드 알림 전송 실패: ${t.message}", t)
            }
        })
    }
}