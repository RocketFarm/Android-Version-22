package com.fanpower.lib.api.model

import java.io.Serializable

data class PickForAdapter(val pick : Pick, var hasDoneAnimation : Boolean = false) : Serializable
