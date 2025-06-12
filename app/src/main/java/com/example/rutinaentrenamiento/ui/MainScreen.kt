package com.example.rutinaentrenamiento.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rutinaentrenamiento.R

@Composable
fun MainScreen(navController: NavController, btManager: BluetoothManager) {
    // 1) Medimos ancho/alto en dp
    val config    = LocalConfiguration.current
    val screenWdp = config.screenWidthDp.toFloat()
    val screenHdp = config.screenHeightDp.toFloat()

    // 2) Detectamos móvil vs tablet
    val isTablet = screenWdp >= 600f

    // 3) Factores de layout adaptados
    val horizFrac   = 0.85f
    val topPadFrac  = if (isTablet) 0.08f else 0.12f
    val botPadFrac  = if (isTablet) 0.08f else 0.12f
    val btnHFrac    = 0.08f
    val spacerHFrac = 0.04f

    // 4) Factores de texto
    val titleSpFrac = 24f  / 360f
    val bodySpFrac  = 16f  / 360f

    // 5) Convertimos a Dp / Sp
    val titleSp    = (screenWdp * titleSpFrac).sp
    val bodySp     = (screenWdp * bodySpFrac).sp

    val horizPadDp = ((1 - horizFrac) / 2 * screenWdp).dp
    val topPadDp   = (screenHdp * topPadFrac).dp
    val botPadDp   = (screenHdp * botPadFrac).dp
    val btnHeight  = (screenHdp * btnHFrac).dp
    val spacerH    = (screenHdp * spacerHFrac).dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizPadDp)
    ) {
        // 1) TÍTULO
        Text(
            text       = "Rutina de entrenamiento",
            fontSize   = titleSp,
            fontWeight = FontWeight.Bold,
            color      = Color(0xFF031966),
            textAlign  = TextAlign.Center,
            modifier   = Modifier
                .align(Alignment.TopCenter)
                .padding(top = topPadDp)
                .fillMaxWidth()
        )

        // 2) IMAGEN + TEXTO central
        Column(
            modifier            = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter        = painterResource(R.drawable.rux_principal),
                contentDescription = null,
                contentScale   = ContentScale.Fit,
                modifier       = Modifier
                    .fillMaxWidth(horizFrac)
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.height(spacerH))
            Text(
                text       = "¡Entrena con Rux para tener una vida más saludable!",
                fontSize   = bodySp,
                fontWeight = FontWeight.Medium,
                color      = Color(0xFF264653),
                textAlign  = TextAlign.Center,
                modifier   = Modifier.fillMaxWidth()
            )
        }

        // 3) BOTÓN principal
        Button(
            onClick = {
                btManager.sendCommand("SELECTION")
                navController.navigate("routine_selection")
            },
            shape  = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF031966),
                contentColor   = Color.White
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = botPadDp)
                .fillMaxWidth(horizFrac)
                .height(btnHeight)
        ) {
            Text(
                "Empezar ahora",
                fontSize   = bodySp,
                textAlign  = TextAlign.Center,
                modifier   = Modifier.fillMaxWidth()
            )
        }
    }
}
