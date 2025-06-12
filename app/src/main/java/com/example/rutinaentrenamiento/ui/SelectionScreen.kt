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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rutinaentrenamiento.R

@Composable
fun RoutineSelectionScreen(
    navController: NavController,
    btManager: BluetoothManager
) {
    // Medir ancho/alto en dp
    val config    = LocalConfiguration.current
    val screenWdp = config.screenWidthDp.toFloat()
    val screenHdp = config.screenHeightDp.toFloat()

    // ¿Tablet o móvil?
    val isTablet = screenWdp >= 600f

    // Factores distintos según dispositivo
    val horizFrac     = if (isTablet) 0.85f else 0.90f
    val vertPadFrac   = if (isTablet) 0.12f else 0.10f
    val titleSpFrac   = if (isTablet) 0.06f else 0.08f
    val titleCardFrac = if (isTablet) 0.05f else 0.065f
    val descSpFrac    = if (isTablet) 0.035f else 0.05f
    val buttonSpFrac  = if (isTablet) 0.05f else 0.06f
    val buttonHFrac   = if (isTablet) 0.08f else 0.10f
    val cardWFrac     = if (isTablet) 0.70f else 0.85f
    val imageFrac     = if (isTablet) 0.60f else 0.75f
    val spacerSmallH  = if (isTablet) 0.02f else 0.015f

    // Convertir a dp / sp
    val horizPadDp   = ((1 - horizFrac) / 2 * screenWdp).dp
    val vertPadDp    = (screenHdp * vertPadFrac).dp
    val btnHeightDp  = (screenHdp * buttonHFrac).dp

    val titleFontSp  = (screenWdp * titleSpFrac).sp
    val titleCardSp  = (screenWdp * titleCardFrac).sp
    val descFontSp   = (screenWdp * descSpFrac).sp
    val btnFontSp    = (screenWdp * buttonSpFrac).sp

    val cardWidthDp  = (screenWdp * cardWFrac).dp
    val imageSizeDp  = (cardWidthDp.value * imageFrac).dp
    val spacerDp     = (screenHdp * spacerSmallH).dp

    // rutinas
    val routines = listOf(
        Routine("Movilidad Articular",
            "Movimientos suaves para preparar tu cuerpo, aumentar flexibilidad y prevenir lesiones.",
            R.drawable.movilidad_portada),
        Routine("Fortalecimiento de Piernas",
            "Ejercicios para fortalecer tus piernas.",
            R.drawable.piernas_portada),
        Routine("Ejercicios para brazos",
            "Entrenamiento para fortalecer brazos y mejorar movilidad.",
            R.drawable.brazos_portada),
        Routine("Equilibrio y Estabilidad",
            "Ejercicios para trabajar el equilibrio y prevenir caídas.",
            R.drawable.equilibrio_estabilidad_portada),
        Routine("Relajación y Estiramiento",
            "Ejercicios suaves para relajar y mejorar elasticidad.",
            R.drawable.relajacion_estiramiento_portada),
        Routine("Ejercicio Completo",
            "Una rutina que combina movilidad, fuerza y relajación.",
            R.drawable.ejercicio_completo)
    )
    val pagerState = rememberPagerState(pageCount = { routines.size })

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = horizPadDp, vertical = vertPadDp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Título
        Text(
            "Selecciona tu Rutina",
            fontSize   = titleFontSp,
            fontWeight = FontWeight.Bold,
            color      = Color(0xFF031966),
            textAlign  = TextAlign.Center,
            modifier   = Modifier.fillMaxWidth()
        )
        // Carrusel
        HorizontalPager(
            state       = pagerState,
            modifier    = Modifier
                .fillMaxWidth()
                .weight(1f),
            pageSpacing = spacerDp
        ) { page ->
            Box(
                Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                RoutineCard(
                    routine   = routines[page],
                    widthDp   = cardWidthDp,
                    imageSize = imageSizeDp,
                    titleSp   = titleCardSp,
                    descSp    = descFontSp,
                    spacerDp  = spacerDp
                )
            }
        }
        // Botón
        Button(
            onClick = {
                val sel = routines[pagerState.currentPage]
                val cmd = when (sel.name) {
                    "Movilidad Articular"       -> "MOVILIDAD_DETAIL"
                    "Fortalecimiento de Piernas"-> "PIERNAS_DETAIL"
                    "Ejercicios para brazos"    -> "BRAZOS_DETAIL"
                    "Equilibrio y Estabilidad"  -> "EQUILIBRIO_DETAIL"
                    "Relajación y Estiramiento" -> "RELAX_DETAIL"
                    "Ejercicio Completo"        -> "COMPLETO_DETAIL"
                    else                        -> sel.name.uppercase().replace(" ", "_")
                }
                btManager.sendCommand(cmd)
                navController.navigate("routine_detail/${sel.name}")
            },
            shape  = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF031966),
                contentColor   = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth(horizFrac)
                .height(btnHeightDp)
        ) {
            Text(
                "Iniciar rutina",
                fontSize   = btnFontSp,
                textAlign  = TextAlign.Center,
                modifier   = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun RoutineCard(
    routine   : Routine,
    widthDp   : Dp,
    imageSize : Dp,
    titleSp   : TextUnit,
    descSp    : TextUnit,
    spacerDp  : Dp
) {
    Card(
        modifier  = Modifier
            .width(widthDp)
            .wrapContentHeight(),
        shape     = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors    = CardDefaults.cardColors(containerColor = Color(0xFFdbe2fa))
    ) {
        Column(
            Modifier
                .padding(spacerDp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter           = painterResource(id = routine.image),
                contentDescription= routine.name,
                modifier          = Modifier.size(imageSize),
                contentScale      = ContentScale.Crop
            )

            Spacer(Modifier.height(spacerDp))

            Text(
                routine.name,
                fontSize   = titleSp,
                fontWeight = FontWeight.Bold,
                textAlign  = TextAlign.Center,
                modifier   = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(spacerDp / 2))

            Text(
                routine.description,
                fontSize   = descSp,
                textAlign  = TextAlign.Center,
                modifier   = Modifier.fillMaxWidth()
            )
        }
    }
}
// Modelo de datos
data class Routine(val name: String, val description: String, val image: Int)