package com.example.rutinaentrenamiento.ui

import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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
    val context    = LocalContext.current
    val info       = exerciseRoutines.first { it.name == routineName }
    var currentIndex by remember { mutableStateOf(0) }
    var videoViewRef by remember { mutableStateOf<VideoView?>(null) }

    // Medimos ancho/alto en dp
    val config    = LocalConfiguration.current
    val screenWdp = config.screenWidthDp.toFloat()
    val screenHdp = config.screenHeightDp.toFloat()

    // Factores de layout y texto
    val horizFrac    = 0.85f
    val topPadFrac   = 0.08f
    val botPadFrac   = 0.15f
    val btnHFrac     = 0.08f
    val spacerHFrac  = 0.03f

    val titleSpFrac  = 30f  / 360f
    val bodySpFrac   = 18f  / 360f

    // Convertir a Dp/Sp
    val titleSp      = (screenWdp * titleSpFrac).sp
    val bodySp       = (screenWdp * bodySpFrac).sp

    val horizPadDp   = ((1 - horizFrac)/2 * screenWdp).dp
    val topPadDp     = (screenHdp * topPadFrac).dp
    val botPadDp     = (screenHdp * botPadFrac).dp
    val btnHeightDp  = (screenHdp * btnHFrac).dp
    val spacerDp     = (screenHdp * spacerHFrac).dp

    // URI del vídeo
    val currentUri by remember(currentIndex) {
        mutableStateOf(
            Uri.parse("android.resource://${context.packageName}/${info.videoResIds[currentIndex]}")
        )
    }
    Box(
        Modifier
            .fillMaxSize()
            .padding(horizontal = horizPadDp)
    ) {
        // Título
        Text(
            text       = info.name,
            fontSize   = titleSp,
            fontWeight = FontWeight.Bold,
            color      = Color(0xFF031966),
            textAlign  = TextAlign.Center,
            modifier   = Modifier
                .align(Alignment.TopCenter)
                .padding(top = topPadDp)
                .fillMaxWidth()
        )

        // Vídeo + info
        Column(
            Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = horizPadDp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            key(currentIndex) {
                AndroidView(
                    factory = { ctx ->
                        VideoView(ctx).apply {
                            videoViewRef = this
                            val mc = MediaController(ctx).apply { setAnchorView(this@apply) }
                            setMediaController(mc)
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
            Spacer(Modifier.height(spacerDp))
            Text(
                text     = "Ejercicio ${currentIndex + 1} de ${info.videoResIds.size}",
                fontSize = bodySp
            )
            Spacer(Modifier.height(spacerDp))
            Text(
                text       = info.steps[currentIndex],
                fontSize   = bodySp,
                fontWeight = FontWeight.Medium,
                color      = Color(0xFF264653),
                textAlign  = TextAlign.Center,
                modifier   = Modifier.fillMaxWidth()
            )
        }

        // Botón principal
        Button(
            onClick = {
                val cmdIdx = currentIndex + 1
                if (cmdIdx < info.btCommands.size) btManager.sendCommand(info.btCommands[cmdIdx])
                if (currentIndex < info.videoResIds.lastIndex) currentIndex++
                else navController.navigate("routine_selection")
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
                .height(btnHeightDp)
        ) {
            Text(
                text      = if (currentIndex < info.videoResIds.lastIndex) "Siguiente ejercicio" else "Finalizar",
                fontSize  = bodySp,
                textAlign = TextAlign.Center,
                modifier  = Modifier.fillMaxWidth()
            )
        }
    }
}