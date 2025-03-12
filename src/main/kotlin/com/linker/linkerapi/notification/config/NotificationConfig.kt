package com.linker.linkerapi.notification.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.linker.linkerapi.notification.api.DiscordWebhookApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Configuration
class NotificationConfig {
    
    @Bean
    fun provideDiscordWebhookApi(
        objectMapper: ObjectMapper
    ): DiscordWebhookApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://discord.com/api/")
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()

        return retrofit.create(DiscordWebhookApi::class.java)
    }
}