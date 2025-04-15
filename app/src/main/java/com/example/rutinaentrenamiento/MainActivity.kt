package com.example.rutinaentrenamiento

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.rutinaentrenamiento.ui.MainScreen
import com.example.rutinaentrenamiento.ui.Navigation
import com.example.rutinaentrenamiento.ui.theme.RutinaEntrenamientoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RutinaEntrenamientoTheme {
                val navController = rememberNavController()
                Navigation(navController)
            }

        }


    }
}

