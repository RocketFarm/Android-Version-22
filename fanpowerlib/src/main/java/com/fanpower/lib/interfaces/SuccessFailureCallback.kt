package com.fanpower.lib.interfaces

import com.fanpower.lib.api.model.MessageResponse

interface SuccessFailureCallback {

    fun onSuccess()
    fun onFailure(messageResponse: MessageResponse)
}