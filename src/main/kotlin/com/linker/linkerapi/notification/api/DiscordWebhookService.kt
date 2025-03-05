package com.linker.linkerapi.notification.api

import com.linker.linkerapi.notification.dto.DiscordWebhookRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface DiscordWebhookService {
    @POST
    fun executeWebhook(
        @Url webhookUrl: String,
        @Body request: DiscordWebhookRequest
    ): Call<Void>
}