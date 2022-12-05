package com.example.appli_mobile_clemence_pierre.test

import androidx.annotation.DrawableRes
import com.example.appli_mobile_clemence_pierre.R

data class MatchProfile(
    val name: String,
    @DrawableRes val drawableResId: Int,
)

val profiles = listOf(
    MatchProfile("Erlich Bachman", R.drawable.ic_baseline_add_24),
    MatchProfile("Richard Hendricks", R.drawable.ic_launcher_foreground),
    MatchProfile("Laurie Bream", R.drawable.ic_launcher_background),
//    MatchProfile("Russ Hanneman", R.drawable.),
//    MatchProfile("Dinesh Chugtai", R.drawable.dinesh),
//    MatchProfile("Monica Hall", R.drawable.monica),
//    MatchProfile("Bertram Gilfoyle", R.drawable.gilfoyle),
//    MatchProfile("Peter Gregory", R.drawable.peter),
//    MatchProfile("Jared Dunn", R.drawable.jared),
//    MatchProfile("Nelson Bighetti", R.drawable.big_head),
//    MatchProfile("Gavin Belson", R.drawable.gavin),
//    MatchProfile("Jian Yang", R.drawable.jian),
//    MatchProfile("Jack Barker", R.drawable.barker),
)
