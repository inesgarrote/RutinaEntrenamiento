package com.example.rutinaentrenamiento.ui

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import java.io.IOException
import java.util.UUID

class BluetoothManager(private val ctx: Context) {
    companion object {
        private const val TAG = "BluetoothManager"
        private const val ROBOT_MAC = "22:22:7E:56:08:00"
        private val APP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }

    // Obtenemos el adapter desde el servicio del sistema
    private val adapter: BluetoothAdapter? by lazy {
        Log.d(TAG, "Inicializando BluetoothAdapter desde sistema")
        val sysBtMgr = ctx.getSystemService(Context.BLUETOOTH_SERVICE)
                as android.bluetooth.BluetoothManager
        sysBtMgr.adapter
    }

    private var clientThread: BluetoothClientThread? = null

fun connect(onConnected: () -> Unit = {}, onError: (Exception) -> Unit = {}) {
    Log.d(TAG, "connect(): iniciando conexión con MAC=$ROBOT_MAC")
    // Solo en Android 12+ existe el permiso BLUETOOTH_CONNECT
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (ContextCompat.checkSelfPermission(
                ctx, Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            onError(SecurityException("Falta permiso BLUETOOTH_CONNECT"))
            return
        }
    }
    // 2) obtenemos el dispositivo
    val device = try {
        adapter!!.getRemoteDevice(ROBOT_MAC)
    } catch (e: Exception) {
        onError(IllegalStateException("Bluetooth no disponible o MAC inválida", e))
        return
    }
    //  3) Lanzamos el thread cliente
        clientThread = BluetoothClientThread(ctx, device, APP_UUID,
            onConnected = {
                Log.d(TAG, "connect(): onConnected callback")
                onConnected()
            },
            onError = { e ->
                Log.e(TAG, "connect(): onError callback: ${e.message}", e)
                onError(e)
            }
        ).also { it.start() }
}

    fun sendCommand(cmd: String) {
        Log.d(TAG, "sendCommand(): vamos a enviar “$cmd”")
        clientThread?.send(cmd)
            ?: Log.w(TAG, "sendCommand(): no hay conexión, comando descartado")
    }

    fun close() {
        Log.d(TAG, "close(): cerrando conexión")
        clientThread?.cancel()
    }
}


private class BluetoothClientThread(
    private val context: Context,
    private val device: BluetoothDevice,
    private val uuid: UUID,
    private val onConnected: () -> Unit,
    private val onError: (Exception) -> Unit
) : Thread() {

    private var socket: BluetoothSocket? = null
    private val TAG = "BluetoothClientThread"

override fun run() {
    Log.d(TAG, "run(): hilo comenzado, estableciendo conexión…")

    // Solo en Android 12+ existe BLUETOOTH_CONNECT como permiso de peligro
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e(TAG, "run(): falta permiso BLUETOOTH_CONNECT al conectar")
            onError(SecurityException("Falta permiso BLUETOOTH_CONNECT al conectar"))
            return
        }
    }
    try {
        // Intentar socket seguro
        socket = device.createRfcommSocketToServiceRecord(uuid).also {
            Log.d(TAG, "run(): socket seguro creado, UUID=$uuid")
        }
        (context.getSystemService(Context.BLUETOOTH_SERVICE)
                as android.bluetooth.BluetoothManager)
            .adapter
            ?.cancelDiscovery()
        Log.d(TAG, "run(): discovery cancelado (seguro)")
        socket!!.connect()
        Log.d(TAG, "run(): connect() completado con éxito (seguro)")
        onConnected()
    } catch (e: IOException) {
        Log.w(TAG, "run(): fallo connect seguro, probando insecure…", e)
        try {
            // Fallback a socket inseguro
            socket = device.createInsecureRfcommSocketToServiceRecord(uuid).also {
                Log.d(TAG, "run(): socket inseguro creado, UUID=$uuid")
            }
            (context.getSystemService(Context.BLUETOOTH_SERVICE)
                    as android.bluetooth.BluetoothManager)
                .adapter
                ?.cancelDiscovery()
            Log.d(TAG, "run(): discovery cancelado (insecure)")
            socket!!.connect()
            Log.d(TAG, "run(): connect() completado con éxito (insecure)")
            onConnected()
        } catch (e2: IOException) {
            Log.e(TAG, "run(): IOException en connect inseguro", e2)
            onError(IOException("Error I/O en connect (secure & insecure)", e2))
            cancel()
        }
    } catch (se: SecurityException) {
        Log.e(TAG, "run(): SecurityException en connect()", se)
        onError(SecurityException("Permiso denegado en conexión Bluetooth", se))
        cancel()
    }
}

    fun send(cmd: String) {
        Log.d(TAG, "send(): preparando envío de “$cmd”")
        // Solo en Android 12+ existe BLUETOOTH_CONNECT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.e(TAG, "send(): permiso BLUETOOTH_CONNECT denegado")
                return
            }
        }

        try {
            socket?.outputStream
                ?.write(cmd.toByteArray(Charsets.UTF_8))
                .also { Log.d(TAG, "send(): comando escrito en socket") }
        } catch (e: SecurityException) {
            Log.e(TAG, "send(): SecurityException al escribir", e)
        } catch (e: IOException) {
            Log.e(TAG, "send(): IOException al escribir", e)
        }
    }

    fun cancel() {
        Log.d(TAG, "cancel(): cerrando socket")
        try {
            socket?.close()
        } catch (_: IOException) { }
    }
}
