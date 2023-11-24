package com.dicoding.pompomwikie.navigation

sealed class Screen (val route : String){
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object Detail : Screen("home/{id}") {
        fun createRoute(id : String) = "home/$id"
    }
}