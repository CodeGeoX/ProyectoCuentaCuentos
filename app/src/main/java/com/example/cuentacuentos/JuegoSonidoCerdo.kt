package com.example.cuentacuentos

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import android.widget.Toast
import java.util.*

class JuegoSonidoCerdo : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var wrong1: AppCompatImageView
    private lateinit var wrong2: AppCompatImageView
    private lateinit var correct: AppCompatImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.juego_sonido_cerdo)

        // Inicializar los elementos de la interfaz
        val playImageView: ImageView = findViewById(R.id.playsound)
        playImageView.setOnClickListener {
            playRandomSound()
        }
        wrong1 = findViewById(R.id.wrong1)
        wrong2 = findViewById(R.id.wrong2)
        correct = findViewById(R.id.correct)

        // Establecer el listener para el botón de reproducir sonidos

        // Establecer el listener para las imágenes de los cerdos
        val imageCerdo: ImageView = findViewById(R.id.onepig)
        val imageserpiente: ImageView = findViewById(R.id.imageserpiente)
        val imageAgila: ImageView = findViewById(R.id.imageagila)

        imageCerdo.setOnClickListener {
            mostrarAlertDialog("¡Correcto!", "¡Has adivinado! Hay tres cerditos.")
            aciertoSound()
            ocultarImagenesIncorrectas()
            correct.visibility = View.VISIBLE
        }

        imageserpiente.setOnClickListener {
            mostrarToast("¡Incorrecto! No hay solo un cerdito.")
            errorSound()
            wrong1.visibility = View.VISIBLE
            wrong2.visibility = View.GONE
            correct.visibility = View.GONE
        }

        imageAgila.setOnClickListener {
            mostrarToast("¡Incorrecto! No hay solo dos cerditos.")
            errorSound()
            wrong2.visibility = View.VISIBLE
            wrong1.visibility = View.GONE
            correct.visibility = View.GONE
        }
    }

    private fun playRandomSound() {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer = null
        }

        val random = Random()
        val randomNumber = random.nextInt(3) + 1

        val soundResourceId = when (randomNumber) {
            1 -> R.raw.oink1
            2 -> R.raw.oink2
            else -> R.raw.oink3
        }

        mediaPlayer = MediaPlayer.create(this, soundResourceId)
        mediaPlayer?.start()

        mediaPlayer?.setOnCompletionListener {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    private fun mostrarAlertDialog(titulo: String, mensaje: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(titulo)
        alertDialogBuilder.setMessage(mensaje)
        alertDialogBuilder.setPositiveButton("Aceptar", null)
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun aciertoSound() {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer = null
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.correctsound)
        mediaPlayer?.start()

        mediaPlayer?.setOnCompletionListener {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    private fun errorSound() {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer = null
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.wrongsound)
        mediaPlayer?.start()

        mediaPlayer?.setOnCompletionListener {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    private fun ocultarImagenesIncorrectas() {
        wrong1.visibility = View.GONE
        wrong2.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        if (mediaPlayer != null) {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
}
