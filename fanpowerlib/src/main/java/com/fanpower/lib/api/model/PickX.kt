package com.fanpower.lib.api.model

data class PickX(
    val created_at: String,
    val display_title: String,
    val fan_picks_count: Int,
    val graded_at: Any,
    val id: String,
    val pick_popularity: Double,
    val selected_tags: List<Any>,
    val state: String,
    val title: String,
    val updated_at: String,
    val user_id: String
)