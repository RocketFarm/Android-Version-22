package com.fanpower.lib.api.model

data class FanPickItem(
    val city: String,
    val claimed_sms_sent_at: Any,
    val country: String,
    val created_at: String,
    val fan_id: Int,
    val frozen_fan_pick_popularity: Double,
    val frozen_points: Int,
    val geo_location: String,
    val graded_at: Any,
    val graded_sms_sent_at: Any,
    val id: String,
    val ip_address: String,
    val pick_id: Int,
    val pick_popularity: Double,
    val prop_id: Int,
    val publisher_id: String,
    val referral_code: Any,
    val short_url: String,
    val source_url: String,
    val state: String,
    val time_zone: String,
    val updated_at: String,
    val zip_code: String
)