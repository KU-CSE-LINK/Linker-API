package com.linker.linkerapi.notification.client

import com.linker.linkerapi.notification.dto.DiscordWebhookRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DiscordWebhookClient {
    @POST(".")
    fun sendMessage(@Body message: DiscordWebhookRequest): Call<Void>
}