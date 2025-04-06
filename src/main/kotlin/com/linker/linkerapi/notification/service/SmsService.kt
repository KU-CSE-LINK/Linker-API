package com.linker.linkerapi.notification.service

import com.linker.linkerapi.rental.entity.Rental
import com.linker.linkerapi.rental.enums.RentalStatus
import net.nurigo.java_sdk.api.Message
import org.json.simple.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
class SmsService(
    @Value("\${coolsms.api-key}")
    private val apiKey: String,
    @Value("\${coolsms.api-secret}")
    private val apiSecret: String,
    @Value("\${coolsms.sender-number}")
    private val senderNumber: String
) {
    private fun sendSms(phoneNumber: String, content: String): JSONObject {
        val profile = System.getProperty("spring.profiles.active")

        if (profile == "local") {
            return JSONObject().apply {
                put("status", "success")
                put("message", "SMS sent successfully")
            }
        }

        val coolsms = Message(apiKey, apiSecret)

        val params = HashMap<String, String>()
        params["to"] = phoneNumber
        params["from"] = senderNumber
        params["type"] = "LMS"
        params["text"] = content

        return coolsms.send(params)
    }

    @Profile("!local")
    fun sendRentalStatusNotification(rental: Rental) {
        val message = when (rental.status) {
            RentalStatus.REQUESTED -> "[Linker 기자재 대여] ${rental.name}님, ${rental.equipment.name} 대여 신청이 접수되었습니다. 준비가 완료되면 문자로 알려드리겠습니다."
            RentalStatus.PREPARED -> "[Linker 기자재 대여] ${rental.name}님, 요청하신 ${rental.equipment.name} 기자재 대여 준비가 완료되었습니다. 과방(공A301)에 방문하여 수령해주세요."
            RentalStatus.RENTED -> "[Linker 기자재 대여] ${rental.name}님, ${rental.equipment.name} 기자재 대여가 완료되었습니다. 반납일: ${rental.returnDate.toLocalDate()}"
            RentalStatus.RETURNED -> "[Linker 기자재 대여] ${rental.name}님, ${rental.equipment.name} 기자재가 정상적으로 반납되었습니다. 이용해주셔서 감사합니다."
        }
        sendSms(rental.phoneNumber, message)
    }

    fun sendReturnDateReminder(rental: Rental) {
        val message = """
        [LINKER 기자재 대여] 안녕하세요, 컴퓨터공학부 학생회 LINK 복지국입니다.
        대여하신 기자재의 반납 기한이 오늘까지입니다. 아직 반납하지 않으셨다면, 오늘 중으로 과방(공A301)에 방문하여 반납해 주시기 바랍니다.
        ※ 기한 내 미반납 시 향후 기자재 대여 서비스 이용에 제한이 있을 수 있습니다.
        감사합니다.
    """.trimIndent()
        sendSms(rental.phoneNumber, message)
    }
}