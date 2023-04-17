package com.fanpower.lib.api.model

import java.io.Serializable

data class Prop(
    val close_at: String,
    val close_time: Any,
    val close_time_id: String,
    val created_at: String,
    val event_id: Int,
    val fan_picks_count: Int,
    val featured_leaderboard_id: Any,
    val id: String,
    val league: String,
    val picks: ArrayList<Pick>,
    val proposition: String,
    val publish_at: Any,
    val search_weight: String,
    val slug: String,
    val status: String,
    val updated_at: String,
    val user_id: Int,
    val utm_content_tag: String,
    val utm_term_tag: String
): Serializable