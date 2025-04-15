package com.example.rutinaentrenamiento.ui

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun MovilidadArticularScreen(navController: NavController) {
    val context = LocalContext.current

    // Inicializa ExoPlayer
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val videoUri = Uri.parse("android.resource://${context.packageName}/${holi}")
            setMediaItem(MediaItem.fromUri(videoUri))
            prepare()
            playWhenReady = false // no auto-play
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Movilidad Articular",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Video con AndroidView + PlayerView
            AndroidView(
                factory = {
                    PlayerView(it).apply {
                        player = exoPlayer
                        useController = true
                        layoutParams = android.view.ViewGroup.LayoutParams(
                            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                            600 // altura deseada del reproductor
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            Text(
                text = "Sigue los movimientos del video para preparar tu cuerpo para la rutina completa.",
                style = MaterialTheme.typography.bodyLarge
            )

            Button(
                onClick = {
                    // Continuar hacia ejercicios detallados o volver
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Comenzar rutina")
            }
        }
    }
}
