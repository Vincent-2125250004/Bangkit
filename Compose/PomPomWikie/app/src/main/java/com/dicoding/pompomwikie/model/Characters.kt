package com.dicoding.pompomwikie.model

data class Characters(
    val id: String,
    val name: String,
    val rarity : String,
    val path : String,
    val element : String,
    val iconChara : String,
    val fullImgChara : String,
    val description : String,
    var isFavorited : Boolean = false
)