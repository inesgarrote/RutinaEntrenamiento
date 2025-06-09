package com.example.rutinaentrenamiento.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rutinaentrenamiento.R

@Composable
fun MainScreen(navController: NavController,   btManager: BluetoothManager) {
    Box(    modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center) {
        // 1) TÍTULO
        Text(
            text = "Rutina de entrenamiento",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF031966),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 120.dp)
                .fillMaxWidth()
        )
        // 2) BLOQUE CENTRAL (imagen + mensaje)
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.rux_principal),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "¡Entrena con Rux para tener una vida más saludable!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF264653),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        // 3) BOTÓN
        Button(
            onClick = {
                // 1) enviamos primero el comando de selección
                btManager.sendCommand("SELECTION")
                // 2) y luego navegamos
                navController.navigate("routine_selection")
            },
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF031966 ),    // azul a medida
                contentColor   = Color.White           // texto en blanco
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp)
                .fillMaxWidth(0.6f)
                .height(60.dp)

        ) {
            Text("Empezar ahora", fontSize = 20.sp)
        }
    }
}
