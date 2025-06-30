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
    ExerciseRoutineInfo(
        name         = "Fortalecimiento de Piernas",
        videoResIds  = listOf(
            R.raw.levantamiento_rodilla,
            R.raw.sentadillas,
            R.raw.elevacion_talones
        ),
        btCommands   = listOf(
            "LEVANTAMIENTO_RODILLAS",
            "SENTADILLAS",
            "ELEVACION_TALONES"
        ),
        steps        = listOf(
            "Levantamiento de rodillas",
            "Sentadillas",
            "Elevación de talones"
        ),
        route        = "fortalecimiento_piernas"
    ),
    ExerciseRoutineInfo(
        name         = "Ejercicios para brazos",
        videoResIds  = listOf(
            R.raw.flexion_pared,
            R.raw.elevacion_brazos,
            R.raw.presion_palmas
        ),
        btCommands   = listOf(
            "FLEXIONES_BRAZOS",
            "ELEVACION_BRAZOS",
            "PRESION_PALMAS"
        ),
        steps        = listOf(
            "Flexiones en la pared",
            "Elevación lateral de brazos",
            "Presión de palmas"
        ),
        route        = "ejercicios_brazos"
    ),
    ExerciseRoutineInfo(
        name         = "Equilibrio y Estabilidad",
        videoResIds  = listOf(
            R.raw.caminar_linea,
            R.raw.equilibrio_pierna,
            R.raw.pasos_laterales
        ),
        btCommands   = listOf(
            "CAMINAR_LINEA",
            "EQUILIBRIO_PIERNA",
            "PASOS_LATERALES"
        ),
        steps        = listOf(
            "Caminar en línea recta",
            "equilibrio en una pierna",
            "Pasos laterales"
        ),
        route        = "equilibrio_estabilidad"
    ),
    ExerciseRoutineInfo(
        name         = "Relajación y Estiramiento",
        videoResIds  = listOf(
            R.raw.estiramiento_brazos,
            R.raw.inclinacion_torso,
            R.raw.estiramiento_piernas
        ),
        btCommands   = listOf(
            "ESTIRAMIENTO_BRAZOS",
            "INCLINACION_TORSO",
            "ESTIRAMIENTO_PIERNAS"
        ),
        steps        = listOf(
            "Estiramiento de brazos",
            "Inclinación de torso",
            "Estiramiento de piernas"
        ),
        route        = "relajacion_estiramiento"
    ),
    ExerciseRoutineInfo(
        name         = "Ejercicio Completo",
        videoResIds  = listOf(
            R.raw.marcha,
            R.raw.sentadillas,
            R.raw.flexion_pared,
            R.raw.equilibrio_pierna,
            R.raw.inclinacion_torso
        ),
        btCommands   = listOf(
            "MARCHA_COMPLETO",
            "SENTADILLAS_COMPLETO",
            "FLEXIONES_BRAZOS_COMPLETO",
            "EQUILIBRIO_PIERNA_COMPLETO",
            "INCLINACION_TORSO_COMPLETO"
        ),
        steps        = listOf(
            "Marcha en el sitio",
            "Sentadillas",
            "Flexiones en la pared",
            "Equilibrio en una pierna",
            "Inclinación de torso"
        ),
        route        = "ejercicio_completo"
    )

)