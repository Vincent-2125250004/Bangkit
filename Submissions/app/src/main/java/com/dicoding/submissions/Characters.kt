package com.dicoding.submissions

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Characters(
    val name: String,
    val rarity: String,
    val path: String,
    val element: String,
    val icon: String,
    val splashArt: String,
    val description : String
) : Parcelable
