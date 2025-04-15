package com.example.rutinaentrenamiento.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") { MainScreen(navController) }
        composable("routine_selection") { RoutineSelectionScreen(navController) }
        composable("movilidad_articular") {
            MovilidadArticularScreen(navController)
        }

    }
}