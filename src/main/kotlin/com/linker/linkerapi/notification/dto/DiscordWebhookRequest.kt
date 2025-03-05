package com.linker.linkerapi.notification.dto

data class DiscordWebhookRequest(
    val content: String? = null,
    val username: String? = null,
    val avatar_url: String? = null,
    val tts: Boolean = false,
    val embeds: List<DiscordEmbed>? = null
)

data class DiscordEmbed(
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val color: Int? = null,
    val author: DiscordEmbedAuthor? = null,
    val fields: List<DiscordEmbedField>? = null,
    val thumbnail: DiscordEmbedThumbnail? = null,
    val footer: DiscordEmbedFooter? = null,
    val timestamp: String? = null
)

data class DiscordEmbedAuthor(
    val name: String,
    val url: String? = null,
    val icon_url: String? = null
)

data class DiscordEmbedField(
    val name: String,
    val value: String,
    val inline: Boolean = false
)

data class DiscordEmbedThumbnail(
    val url: String
)

data class DiscordEmbedFooter(
    val text: String,
    val icon_url: String? = null
)