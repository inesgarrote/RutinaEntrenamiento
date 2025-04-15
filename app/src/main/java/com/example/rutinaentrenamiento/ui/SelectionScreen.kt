package com.example.rutinaentrenamiento.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rutinaentrenamiento.R

@Composable
fun RoutineSelectionScreen(navController: NavController) {
    val routines = listOf(
        Routine("Movilidad Articular", "Prepara el cuerpo para el ejercicio con movimientos suaves, además de mejorar la flexibilidad y prevenir lesiones.", R.drawable.portada),
        Routine("Fortalecimiento de Piernas", "Ejercicios para fortalecer tus piernas.", R.drawable.portada),
        Routine("Ejercicios para brazos", "Entrenamiento para fortalecer brazos y mejorar movilidad.", R.drawable.portada),
        Routine("Equilibrio y Estabilidad", "Ejercicios para trabajar el equilibrio y prevenir caídas.", R.drawable.portada),
        Routine("Relajación y Estiramiento", "Ejercicios suaves para relajar y mejorar elasticidad.", R.drawable.portada),
        Routine("Ejercicio Completo", "Una rutina que combina movilidad, fuerza y relajación.", R.drawable.portada)
    )

    val pagerState = rememberPagerState(pageCount = { routines.size })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Selecciona tu Rutina",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 50.dp)
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            pageSpacing = 16.dp, //  espaciado entre páginas
        ) { page ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                RoutineCard(routine = routines[page])
            }
        }

        Button(
            onClick = {
                val selectedRoutine = routines[pagerState.currentPage]
                when (selectedRoutine.name) {
                    "Movilidad Articular" -> navController.navigate("movilidad_articular")
                    "Fortalecimiento de Piernas" -> navController.navigate("piernas")
                    "Ejercicios para brazos" -> navController.navigate("brazos")
                    "Equilibrio y Estabilidad" -> navController.navigate("equilibrio")
                    "Relajación y Estiramiento" -> navController.navigate("relajacion")
                    "Ejercicio Completo" -> navController.navigate("ejercicio_completo")
                    else -> navController.navigate("routine_detail/${selectedRoutine.name}")
                }
            },
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(60.dp)
        ) {
            Text("Iniciar rutina", fontSize = 20.sp)
        }
    }
}


// Modelo de datos para las rutinas
data class Routine(val name: String, val description: String, val image: Int)

// Composable para cada tarjeta del carrusel
@Composable
fun RoutineCard(routine: Routine) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .padding(16.dp)
            .size(300.dp, 400.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = routine.image),
                contentDescription = routine.name,
                modifier = Modifier.size(220.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = routine.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = routine.description,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}