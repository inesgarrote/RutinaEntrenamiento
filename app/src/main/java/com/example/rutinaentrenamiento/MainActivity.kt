package com.example.rutinaentrenamiento

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.rutinaentrenamiento.ui.BluetoothManager
import com.example.rutinaentrenamiento.ui.Navigation

class MainActivity : ComponentActivity() {
    private lateinit var btManager: BluetoothManager

    companion object {
        private const val TAG = "MainActivity"
        private const val PERM_REQUEST = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btManager = BluetoothManager(this)

        // 1) Pedir permisos y, si ya los tienes, conectar
        val toRequest = requestPermissionsIfNeeded()
        if (toRequest.isEmpty()) {
            // Ya tienes todos los permisos: conecta
            startBluetoothConnect()
        }

        // 2) Levantar la UI
        setContent {
                val navController = rememberNavController()
                Navigation(navController, btManager)
        }
    }

    private fun startBluetoothConnect() {
        btManager.connect(
            onConnected = {
                runOnUiThread {
                    Toast.makeText(this, "Conectado con Rux", Toast.LENGTH_LONG).show()
                }
                Log.d(TAG, "✅ onConnected")
            },
            onError = { e ->
                runOnUiThread {
                    Toast.makeText(this, "Error conectando: ${e.message}", Toast.LENGTH_LONG).show()
                }
                Log.e(TAG, "❌ onError: ${e.message}", e)
            }
        )
    }

    /**
     * Solicita permisos necesarios y devuelve la lista
     * de los que faltan por conceder.
     */
    private fun requestPermissionsIfNeeded(): List<String> {
        val perms = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            perms += Manifest.permission.BLUETOOTH_CONNECT
            perms += Manifest.permission.BLUETOOTH_SCAN
        }
        // Filtramos los que NO estén concedidos
        val toRequest = perms.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        if (toRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, toRequest.toTypedArray(), PERM_REQUEST)
        }
        return toRequest
    }

    /**
     * Recibimos aquí el resultado de la petición de permisos.
     * Si ya están todos concedidos, lanzamos la conexión.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERM_REQUEST) {
            // Verificamos que todos hayan sido concedidos
            val denied = grantResults.any { it != PackageManager.PERMISSION_GRANTED }
            if (denied) {
                Toast.makeText(this,
                    "Necesito permisos Bluetooth para conectar",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                // Ahora sí, iniciamos la conexión
                startBluetoothConnect()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        btManager.close()
    }
}
