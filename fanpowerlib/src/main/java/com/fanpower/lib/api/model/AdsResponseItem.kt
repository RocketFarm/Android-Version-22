package com.fanpower.lib.api.model

data class AdsResponseItem(
    val ad_copy: String,
    val ad_end_time: Any,
    val ad_image: String,
    val ad_name: String,
    val ad_start_time: String,
    val ad_url: String,
    val ad_zone_id: String,
    val author_publisher_id: String,
    val background_color: Any,
    val background_image: String,
    val background_type: String,
    val created_at: String,
    val deleted_at: Any,
    val every_pick_sms: Boolean,
    val first_pick_sms: Boolean,
    val foreground_image: String,
    val id: String,
    val publishers: List<Any>,
    val sms_message: String,
    val states: List<Any>,
    val status: String,
    val tags: List<Any>,
    val updated_at: String,
    val zones: List<Zone>
)