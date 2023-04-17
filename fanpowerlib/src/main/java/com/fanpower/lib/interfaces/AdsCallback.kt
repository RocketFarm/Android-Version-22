package com.fanpower.lib.interfaces

import com.fanpower.lib.api.model.AdsResponseItem
import com.fanpower.lib.api.model.MessageResponse

interface AdsCallback {

    fun onSuccess(list : List<AdsResponseItem>?)
    fun onFailure(messageResponse: MessageResponse)
}