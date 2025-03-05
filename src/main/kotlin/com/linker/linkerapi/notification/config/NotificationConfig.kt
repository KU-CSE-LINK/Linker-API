package com.linker.linkerapi.notification.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.linker.linkerapi.notification.api.DiscordWebhookService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

@Configuration
class NotificationConfig {

    @Bean
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }


    @Bean
    fun provideDiscordWebhookService(
        okHttpClient: OkHttpClient,
        objectMapper: ObjectMapper
    ): DiscordWebhookService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://discord.com/api/")
            .client(okHttpClient)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()

        return retrofit.create(DiscordWebhookService::class.java)
    }
}