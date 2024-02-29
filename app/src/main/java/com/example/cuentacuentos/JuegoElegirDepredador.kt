package com.example.cuentacuentos

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class JuegoElegirDepredador : AppCompatActivity() {

    private lateinit var aciertoSound: MediaPlayer
    private lateinit var errorSound: MediaPlayer
    private lateinit var wrong1: ImageView
    private lateinit var wrong2: ImageView
    private lateinit var correct: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.juego_elegir_depredador)

        // Inicializar los MediaPlayer con los sonidos
        aciertoSound = MediaPlayer.create(this, R.raw.correctsound)
        errorSound = MediaPlayer.create(this, R.raw.wrongsound)

        val imageEagle: ImageView = findViewById(R.id.imageeagle)
        val imageWolf: ImageView = findViewById(R.id.imagewolf)
        val imageSnake: ImageView = findViewById(R.id.imagesnake)
        wrong1 = findViewById(R.id.wrong1)
        wrong2 = findViewById(R.id.wrong2)
        correct = findViewById(R.id.correct)

        imageEagle.setOnClickListener {
            // Mostrar Toast cuando el usuario se equivoque
            mostrarToast("¡Incorrecto! El águila no es el depredador correcto.")
            // Reproducir sonido de error
            errorSound.start()
            // Mostrar la imagen incorrecta
            wrong1.visibility = View.VISIBLE
            // Ocultar las otras imágenes
            wrong2.visibility = View.GONE
            correct.visibility = View.GONE
        }

        imageWolf.setOnClickListener {
            mostrarAlertDialog("Correcte!", "El llop és el depredador correcte.")
            aciertoSound.start()
            wrong1.visibility = View.GONE
            wrong2.visibility = View.GONE
            correct.visibility = View.VISIBLE
        }

        imageSnake.setOnClickListener {
            // Mostrar Toast cuando el usuario se equivoque
            mostrarToast("¡Incorrecto! La serpiente no es el depredador correcto.")
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
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
