package com.fanpower.lib.api.model

class CreateFanPickResponse(
    val fanPick: String,
    val picks: List<PickX>,
    val short_url: String
)