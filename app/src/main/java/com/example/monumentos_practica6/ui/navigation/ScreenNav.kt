package com.example.monumentos_practica6.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenNav : NavKey {
    @Serializable
    data object Launcher : ScreenNav()

    @Serializable
    data object Monuments : ScreenNav()

    @Serializable
    data class MonumentDetail(val id: Int) : ScreenNav()

    @Serializable
    data object Map : ScreenNav()

    @Serializable
    data object Favorites : ScreenNav()

    @Serializable
    data object MyMonuments : ScreenNav()
}