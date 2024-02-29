package com.example.cuentacuentos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class JuegoCantidadLobos : AppCompatActivity() {

    private lateinit var aciertoSound: MediaPlayer
    private lateinit var errorSound: MediaPlayer
    private lateinit var wrong1: ImageView
    private lateinit var wrong2: ImageView
    private lateinit var correct: ImageView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.juego_cantidad_lobos)

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("Juegos", Context.MODE_PRIVATE)

        // Inicializar los MediaPlayer con los sonidos
        aciertoSound = MediaPlayer.create(this, R.raw.correctsound)
        errorSound = MediaPlayer.create(this, R.raw.wrongsound)

        val imageTresLobos: ImageView = findViewById(R.id.ImageThreeWolves)
        val imageUnLobo: ImageView = findViewById(R.id.ImageOneWolf)
        val imageDosLobos: ImageView = findViewById(R.id.ImageTwoWolves)
        wrong1 = findViewById(R.id.wrong1)
        wrong2 = findViewById(R.id.wrong2)
        correct = findViewById(R.id.correct)

        imageUnLobo.setOnClickListener {
            // Mostrar AlertDialog cuando el usuario acierte
            mostrarAlertDialog("Correcte!", "Només hi ha un llop.")
            // Reproducir sonido de acierto
            aciertoSound.start()
            // Ocultar las imágenes incorrectas
            wrong1.visibility = View.GONE
            wrong2.visibility = View.GONE
            // Mostrar la imagen correcta
            correct.visibility = View.VISIBLE
        }

        imageTresLobos.setOnClickListener {
            // Mostrar Toast cuando el usuario se equivoque
            mostrarToast("Uh-Oh Intenta-ho un altre cop")
            // Reproducir sonido de error
            errorSound.start()
            // Mostrar la imagen incorrecta
            wrong1.visibility = View.VISIBLE
            // Ocultar las otras imágenes
            wrong2.visibility = View.GONE
            correct.visibility = View.GONE
        }

        imageDosLobos.setOnClickListener {
            // Mostrar Toast cuando el usuario se equivoque
            mostrarToast("Uh-Oh Intenta-ho un altre cop")
            // Reproducir sonido de error
            errorSound.start()
            // Mostrar la imagen incorrecta
            wrong2.visibility = View.VISIBLE
            // Ocultar las otras imágenes
            wrong1.visibility = View.GONE
            correct.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Liberar recursos de los MediaPlayer
        aciertoSound.release()
        errorSound.release()
    }

    private fun mostrarAlertDialog(titulo: String, mensaje: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(titulo)
        alertDialogBuilder.setMessage(mensaje)
        alertDialogBuilder.setPositiveButton("Aceptar") { dialog, _ ->
            // Guardar la victoria del juego 3 en SharedPreferences
            sharedPreferences.edit().putBoolean("victoria_juego_3", true).apply()
            // Iniciar la actividad JuegosActivity
            val intent = Intent(this, JuegosActivity::class.java)
            startActivity(intent)
            finish() // Cerrar la actividad actual
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
