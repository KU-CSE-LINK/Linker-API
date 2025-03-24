package com.linker.linkerapi.notification.service

import com.linker.linkerapi.rental.entity.Rental
import com.linker.linkerapi.rental.enums.RentalStatus
import net.nurigo.java_sdk.api.Message
import net.nurigo.java_sdk.exceptions.CoolsmsException
import org.json.simple.JSONObject
import org.springframework.beans.factory.annotation.Value
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
    fun sendSms(phoneNumber: String, content: String): JSONObject {
        val coolsms = Message(apiKey, apiSecret)

        val params = HashMap<String, String>()
        params["to"] = phoneNumber
        params["from"] = senderNumber
        params["type"] = "SMS"
        params["text"] = content

        try {
            val response = coolsms.send(params)
            return response
        } catch (e: CoolsmsException) {
            throw RuntimeException("SMS 발송 실패: ${e.message}", e)
        }
    }

    fun sendTestSms(phoneNumber: String): JSONObject {
        return sendSms(phoneNumber, "[Linker] 테스트 문자입니다.")
    }

    fun sendRentalStatusNotification(rental: Rental) {
        val message = when (rental.status) {
            RentalStatus.PREPARED -> "[Linker 기자재 대여] ${rental.name}님, 요청하신 ${rental.equipment.name} 기자재 대여 준비가 완료되었습니다. 과방(공A301)에 방문하여 수령해주세요."
            RentalStatus.RENTED -> "[Linker 기자재 대여] ${rental.name}님, ${rental.equipment.name} 기자재 대여가 완료되었습니다. 반납일: ${rental.returnDate.toLocalDate()}"
            RentalStatus.RETURNED -> "[Linker 기자재 대여] ${rental.name}님, ${rental.equipment.name} 기자재가 정상적으로 반납되었습니다. 이용해주셔서 감사합니다."
            else -> return
        }

        sendSms(rental.phoneNumber, message)
    }
}