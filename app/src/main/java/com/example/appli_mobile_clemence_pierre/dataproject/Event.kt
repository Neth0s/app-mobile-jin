package com.example.appli_mobile_clemence_pierre.data
import kotlinx.serialization.Serializable

@Serializable
data class Event (
    val characterName : String,
    val charaterFaceUrl : String,
    val description : String,
    val modifiersYes : List<Int>,
    val modifiersNo : List<Int>
)

data class Stats (
    val money : Int,
    val popularity : Int,
    val health : Int
)