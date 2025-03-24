package com.linker.linkerapi.notification.dto

data class SendSmsRequest(
    val phoneNumber: String,
    val content: String
)