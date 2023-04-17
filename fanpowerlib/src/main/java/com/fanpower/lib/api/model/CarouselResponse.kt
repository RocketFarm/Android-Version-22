package com.fanpower.lib.api.model

data class CarouselResponse(
    val active: Boolean,
    val created_at: String,
    val deleted_at: Any,
    val description: String,
    val end_date: Any,
    val id: String,
    val league: String,
    val league_id: Int,
    val name: String,
    val private_props: Boolean,
    val prop_ids: List<PropId>,
    val publisher_id: String,
    val start_date: Any,
    val tags: List<Any>,
    val updated_at: String,
    val value: String
)