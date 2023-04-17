package com.fanpower.lib.interfaces

import com.fanpower.lib.api.model.FanPickItem
import com.fanpower.lib.api.model.MessageResponse

interface FanPickCallback {

    fun onSuccess(list : List<FanPickItem>?)
    fun onFailure(messageResponse: MessageResponse)
}