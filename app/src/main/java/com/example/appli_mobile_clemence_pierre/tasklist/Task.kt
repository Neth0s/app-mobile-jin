package com.example.appli_mobile_clemence_pierre.tasklist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Task(
    @SerialName("id")
    val id: String,
    @SerialName("content")
    var title: String,
    @SerialName("description")
    val description: String = ""
) : java.io.Serializable
