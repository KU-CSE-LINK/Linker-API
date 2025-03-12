package com.linker.linkerapi.notification.api

import com.linker.linkerapi.notification.dto.DiscordWebhookDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface DiscordWebhookApi {
    @POST
    fun executeWebhook(
        @Url webhookUrl: String,
        @Body request: DiscordWebhookDto
    ): Call<Void>
}