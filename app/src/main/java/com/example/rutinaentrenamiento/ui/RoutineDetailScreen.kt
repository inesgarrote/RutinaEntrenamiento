package com.example.rutinaentrenamiento.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rutinaentrenamiento.R
import kotlin.math.roundToInt

@Composable
fun RoutineDetailScreen(
    routineName: String,
    navController: NavController,
    btManager: BluetoothManager
) {
    val info = exerciseRoutines.first { it.name == routineName }

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

        // 2) BLOQUE CENTRAL (imagen + listado)
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen rebotando
            BouncingRuxImage(
                imageRes = R.drawable.rux_hablando,
                modifier = Modifier
                    .size(200.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Ejercicios:",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            info.steps.forEach { step ->
                Text(
                    text = "• $step",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }

        // 3) BOTÓN abajo
        Button(
            onClick = {
                // envía primer comando y navega
                info.btCommands.firstOrNull()?.let { btManager.sendCommand(it) }
                navController.navigate("exercise/${info.name}")
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
                text = "¡Vamos allá!",
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun BouncingRuxImage(
    @DrawableRes imageRes: Int,
    modifier: Modifier = Modifier
) {
    // 1) Crea una transición infinita
    val infiniteTransition = rememberInfiniteTransition()
    // 2) Anima un desplazamiento vertical entre -16.dp y +16.dp
    val offsetY by infiniteTransition.animateFloat(
        initialValue = -16f,
        targetValue  = 16f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Image(
        painter = painterResource(imageRes),
        contentDescription = null,
        modifier = modifier
            .offset { IntOffset(x = 0, y = offsetY.roundToInt()) }
    )
}
