package com.linker.linkerapi.notification.dto

data class DiscordWebhookRequest(
    val content: String,
    val username: String = "Linker 대여 알림",
    val embeds: List<DiscordEmbed> = emptyList()
)

data class DiscordEmbed(
    val title: String,
    val description: String,
    val color: Int = 0x3498DB,
    val fields: List<DiscordField> = emptyList()
)

data class DiscordField(
    val name: String,
    val value: String,
    val inline: Boolean = false
)