package com.fanpower.lib.api.model

data class Zone(
    val created_at: String,
    val deleted_at: Any,
    val description: String,
    val height: Int,
    val id: Int,
    val name: String,
    val sms: Boolean,
    val updated_at: String,
    val width: Int
)