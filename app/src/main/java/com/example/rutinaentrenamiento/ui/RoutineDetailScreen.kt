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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
    // 1) Obtenemos ancho y alto en dp
    val config     = LocalConfiguration.current
    val screenWdp  = config.screenWidthDp.toFloat()
    val screenHdp  = config.screenHeightDp.toFloat()

    // 2) Factores de layout
    val horizFrac     = 0.85f   // 85% de ancho para contenido
    val topPadFrac    = 0.12f   // 12% del alto hasta el título
    val botPadFrac    = 0.10f   // 10% del alto hasta el botón
    val btnHFrac      = 0.08f   // 8% de alto para el botón
    val spacerFrac    = 0.02f   // 2% de alto entre secciones

    // 3) Factores de texto y imagen
    val titleSpFrac   = 24f  / 360f
    val stepTitleFrac = 16f  / 360f
    val stepSpFrac    = 14f  / 360f
    val imageFrac     = 0.6f

    // 4) Calculamos valores en dp / sp
    val titleSp     = (screenWdp * titleSpFrac).sp
    val stepTitleSp = (screenWdp * stepTitleFrac).sp
    val stepSp      = (screenWdp * stepSpFrac).sp

    val horizPadDp  = ((1 - horizFrac) / 2 * screenWdp).dp
    val topPadDp    = (screenHdp * topPadFrac).dp
    val botPadDp    = (screenHdp * botPadFrac).dp
    val btnHeightDp = (screenHdp * btnHFrac).dp
    val spacerDp    = (screenHdp * spacerFrac).dp

    val imageSizeDp = (screenWdp * imageFrac).dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizPadDp),
        contentAlignment = Alignment.Center
    ) {
        // 1) TÍTULO
        Text(
            text       = routineName,
            fontSize   = titleSp,
            fontWeight = FontWeight.Bold,
            color      = Color(0xFF031966),
            textAlign  = TextAlign.Center,
            modifier   = Modifier
                .align(Alignment.TopCenter)
                .padding(top = topPadDp)
                .fillMaxWidth()
        )

        // 2) BLOQUE CENTRAL
        Column(
            modifier           = Modifier.align(Alignment.Center),
            horizontalAlignment= Alignment.CenterHorizontally
        ) {
            BouncingRuxImage(
                imageRes = R.drawable.rux_hablando,
                modifier = Modifier.size(imageSizeDp)
            )

            Text(
                text       = "Ejercicios:",
                fontSize   = stepTitleSp,
                fontWeight = FontWeight.SemiBold,
                textAlign  = TextAlign.Start,
                modifier   = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(spacerDp / 2))

            exerciseRoutines
                .first { it.name == routineName }
                .steps
                .forEach { step ->
                    Text(
                        text      = "• $step",
                        fontSize  = stepSp,
                        modifier  = Modifier
                            .fillMaxWidth()
                            .padding(vertical = spacerDp / 4)
                    )
                }
        }

        // 3) BOTÓN
        Button(
            onClick = {
                val cmd = exerciseRoutines
                    .first { it.name == routineName }
                    .btCommands
                    .firstOrNull()
                cmd?.let { btManager.sendCommand(it) }
                navController.navigate("exercise/$routineName")
            },
            shape   = RoundedCornerShape(25.dp),
            colors  = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF031966),
                contentColor   = Color.White
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = botPadDp)
                .fillMaxWidth(horizFrac)
                .height(btnHeightDp)
        ) {
            Text(
                "¡Vamos allá!",
                fontSize  = stepSp,
                textAlign = TextAlign.Center,
                modifier  = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun BouncingRuxImage(
    @DrawableRes imageRes: Int,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val offsetY by infiniteTransition.animateFloat(
        initialValue    = -16f,
        targetValue     = 16f,
        animationSpec   = infiniteRepeatable(
            animation  = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Image(
        painter           = painterResource(imageRes),
        contentDescription= null,
        contentScale      = ContentScale.Fit,
        modifier          = modifier.offset { IntOffset(0, offsetY.roundToInt()) }
    )
}