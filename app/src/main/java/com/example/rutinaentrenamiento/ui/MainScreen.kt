package com.example.rutinaentrenamiento.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rutinaentrenamiento.R

@Composable
fun MainScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFFf6e8ea)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Rutina de entrenamiento",
                color = Color(0xFF264653),
                fontSize = 40.sp,
                lineHeight = 50.sp, // Ajusta el interlineado
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 50.dp, bottom = 100.dp)

            )


            Image(
                painter = painterResource(id = R.drawable.rux_principal),
                contentDescription = "Imagen de entrenamiento",
                modifier = Modifier.size(250.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "¡Entrena con Rux para tener una vida más saludable!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF264653),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 100.dp, bottom = 24.dp)

            )

            Button(
                onClick = { navController.navigate("routine_selection") },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7f95d1)),
                modifier = Modifier
                    .padding(16.dp)
                    .size(width = 220.dp, height = 60.dp)
                    .shadow(8.dp, shape = RoundedCornerShape(50))
            ) {
                Text(
                    text = "EMPEZAR AHORA",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
