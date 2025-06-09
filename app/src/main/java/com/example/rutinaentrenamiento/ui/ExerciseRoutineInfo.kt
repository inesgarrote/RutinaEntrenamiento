package com.example.rutinaentrenamiento.ui

import com.example.rutinaentrenamiento.R

class ExerciseRoutineInfo(
    val name: String, // Nombre de la rutina
    val videoResIds: List<Int>, // IDs de los vídeos
    val btCommands: List<String>, // Comandos de Bluetooth
    val steps: List<String>,      // Pasos de la rutina
    val route: String // Ruta de la rutina
)

val exerciseRoutines = listOf(
    ExerciseRoutineInfo(
        name         = "Movilidad Articular",
        videoResIds  = listOf(
            R.raw.rotacion_cuello,
            R.raw.rotacion_muneca,
            R.raw.rotacion_brazos,
            R.raw.giro_cintura,
            R.raw.marcha
        ),
        btCommands   = listOf(
            "ROTACION_CUELLO",
            "ROTACION_MUNECA",
            "ROTACION_BRAZOS",
            "GIRO_CINTURA",
            "MARCHA"
        ),
        steps        = listOf(
            "Rotación de cuello",
            "Rotación de muñeca",
            "Rotación de brazos",
            "Giro de cintura",
            "Marcha en el sitio"
        ),
        route        = "movilidad_articular"
    ),

)