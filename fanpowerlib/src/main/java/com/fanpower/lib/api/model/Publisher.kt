package com.fanpower.lib.api.model

data class Publisher(

    val access_token: String,
    val background_color: String,
    val created_at: String,
    val default_image: Any,
    val deleted_at: Any,
    val discord_guild_id: String,
    val display_url: Any,
    val embed_subtitle: String,
    val embed_title: String,
    val icon_color: String,
    val id: String,
    val logo: String,
    val logo_url: String,
    val name: String,
    val outgoing_email_address: String,
    val outgoing_sms_number: String,
    val picker_logo: String,
    val picker_logo_url: String,
    val picker_preference: String,
    val pluto_activation_hook: Boolean,
    val primary_color: String,
    val secondary_color: String,
    val settings: Settings,
    val skip_blur: Boolean,
    val slug: String,
    val source_url: String,
    val tenant_domain: Any,
    val tenant_id: Any,
    val tenant_name: Any,
    val text_link_color: String,
    val updated_at: String

)