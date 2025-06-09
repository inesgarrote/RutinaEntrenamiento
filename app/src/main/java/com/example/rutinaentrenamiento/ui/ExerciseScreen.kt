package com.example.rutinaentrenamiento.ui

import android.net.Uri
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

@Composable
fun ExerciseScreen(
    routineName: String,
    navController: NavController,
    btManager: BluetoothManager
) {
    val context = LocalContext.current
    // Recuperamos la info de la rutina
    val info = exerciseRoutines.first { it.name == routineName }

    var currentIndex by remember { mutableStateOf(0) }
    // URI del vídeo actual
    val currentUri by remember(currentIndex) {
        mutableStateOf(
            Uri.parse("android.resource://${context.packageName}/${info.videoResIds[currentIndex]}")
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        // 1) TÍTULO arriba
        Text(
            text = info.name,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF031966),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 120.dp)
                .fillMaxWidth()
        )

        // 2) BLOQUE CENTRAL (video + progreso + nombre del paso)
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Re-crea el VideoView cada vez que cambie currentIndex
            key(currentIndex) {
                AndroidView(
                    factory = { ctx ->
                        VideoView(ctx).apply {

                            val mc = MediaController(ctx)
                            mc.setAnchorView(this)
                            setMediaController(mc)
                            // Carga y arranca
                            setVideoURI(currentUri)
                            setOnPreparedListener { start() }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(20.dp))
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Progreso
            Text(
                text = "Ejercicio ${currentIndex + 1} de ${info.videoResIds.size}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre del paso actual
            Text(
                text = info.steps[currentIndex],
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF264653),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // 3) BOTÓN abajo
        Button(
            onClick = {
                // 1) Calcula el índice del comando (offset +1)
                val cmdIdx = currentIndex + 1

                // 2) Solo envía si está dentro de bounds
                if (cmdIdx < info.btCommands.size) {
                    btManager.sendCommand(info.btCommands[cmdIdx])
                }

                // Avanzar o terminar
                if (currentIndex < info.videoResIds.lastIndex) {
                    currentIndex++
                } else {
                    navController.navigate("routine_selection")
                }
            },
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF031966),
                contentColor   = Color.White
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp)
                .fillMaxWidth(0.6f)
                .height(60.dp)
        ) {
            Text(
                text = if (currentIndex < info.videoResIds.lastIndex)
                    "Siguiente ejercicio"
                else
                    "Finalizar",
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}