package com.linker.linkerapi.notification.controller

import com.linker.linkerapi.common.annotation.AdminUsername
import com.linker.linkerapi.notification.dto.SendSmsRequest
import com.linker.linkerapi.notification.service.SmsService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "SMS Controller")
@RestController
@RequestMapping("/admin")
class SmsController(
    private val smsService: SmsService
) {
    @Operation(summary = "SMS 발송", description = "관리자가 특정 번호로 SMS를 발송합니다.")
    @PostMapping("/sms/send")
    fun sendSms(
        @AdminUsername username: String,
        @RequestBody request: SendSmsRequest
    ): HashMap<String, *> {
        return smsService.sendSms(request.phoneNumber, request.content) as HashMap<String, *>
    }

    @Operation(summary = "SMS 테스트 발송", description = "테스트 SMS를 발송합니다.")
    @PostMapping("/sms/test")
    fun testSms(
        @AdminUsername username: String,
        @RequestParam phoneNumber: String
    ): HashMap<String, *> {
        return smsService.sendTestSms(phoneNumber) as HashMap<String, *>
    }
}