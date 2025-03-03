package com.linker.linkerapi.notification.config

import com.linker.linkerapi.notification.client.DiscordWebhookClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Configuration
class NotificationConfig {
    @Value("\${discord.webhook.url}")
    private lateinit var discordWebhookUrl: String

    @Bean
    fun discordWebhookClient(): DiscordWebhookClient? {
        if (discordWebhookUrl.isBlank()) {
            return null
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(discordWebhookUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        return retrofit.create(DiscordWebhookClient::class.java)
    }
}