# 🧠 Rutina de entrenamiento con Rux

Aplicación Android desarrollada para tabletas, que permite seleccionar, visualizar y ejecutar rutinas de ejercicio físico asistido, en coordinación con el robot **Rux** mediante conectividad Bluetooth Classic.

---

## 📱 Descripción

La app permite al usuario:
- Seleccionar una rutina de entrenamiento entre varias disponibles.
- Visualizar los vídeos de cada ejercicio en la tablet, mientras Rux narra las instrucciones, realiza gestos y cambia de expresión de forma sincronizada, actuando como guía motivacional
- Avanzar paso a paso a través de la rutina.

El sistema combina una **interfaz moderna con Jetpack Compose** y comunicación en tiempo real con el robot a través de comandos enviados vía **RFCOMM** (Bluetooth Classic).

---

## 🤖 Requisitos

- **Tablet con Android 11 o superior**
- **Permisos de Bluetooth y localización**
- **Robot Rux** con la app receptora en ejecución

---

## 🔧 Tecnologías usadas

- Android Studio (Kotlin)
- Jetpack Compose (UI moderna)
- Android Media3 (para reproducción de vídeo)
- Conexión Bluetooth RFCOMM (socket inseguro como fallback)
- Arquitectura modular dividida en:
  - Capa de presentación (pantallas)
  - Modelo de datos (`ExerciseRoutineInfo`)
  - Gestión de Bluetooth (`BluetoothManager` + `BluetoothClientThread`)

---

## 🧪 Ejecución

1. Ejecutar la app **EjerciciosConRux** en Rux
2. Abrir la app **RutinaEntrenamiento** en la tablet.
3. Seleccionar la rutina deseada.
4. Rux comenzará a hablar, gesticular y moverse en sincronía con los ejercicios.

---

## 🧩 Licencia

Este proyecto forma parte de un Trabajo de Fin de Grado. Uso educativo y experimental.
