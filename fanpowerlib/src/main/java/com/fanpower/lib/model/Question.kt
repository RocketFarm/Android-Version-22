package com.fanpower.lib.model

import java.io.Serializable
import kotlin.collections.ArrayList


data class Question(
    var title: String,
    var answers: ArrayList<Answer>
) : Serializable