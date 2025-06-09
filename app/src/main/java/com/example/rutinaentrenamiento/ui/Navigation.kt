package com.example.rutinaentrenamiento.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument

@Composable
fun Navigation(navController: NavHostController, btManager: BluetoothManager) {
    NavHost(navController = navController, startDestination = "main_screen") {
        // 1) Pantalla principal
        composable("main_screen") {
            MainScreen(navController, btManager)
        }
        // 2) Pantalla de selección de rutina
        composable("routine_selection") {

            RoutineSelectionScreen(navController, btManager)
        }
        // 3) Pantalla de introducción a la rutina
        composable(
            route = "routine_detail/{routineName}",
            arguments = listOf(navArgument("routineName") {
                type = NavType.StringType
            })
        ) { backStack ->
            val name = backStack.arguments!!.getString("routineName")!!
            RoutineDetailScreen(name, navController, btManager)
        }
        // 4) Pantalla de ejercicios
        composable(
            route = "exercise/{routineName}",
            arguments = listOf(navArgument("routineName"){ type = NavType.StringType })
        ) { backStack ->
            val name = backStack.arguments!!.getString("routineName")!!
            ExerciseScreen(name, navController, btManager)
        }
    }
}
