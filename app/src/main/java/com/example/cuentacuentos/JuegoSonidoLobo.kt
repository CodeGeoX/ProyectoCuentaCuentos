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

class JuegoSonidoLobo : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var wrong1: AppCompatImageView
    private lateinit var wrong2: AppCompatImageView
    private lateinit var correct: AppCompatImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.juego_sonido_lobo)

        val playImageView: ImageView = findViewById(R.id.playsound)
        playImageView.setOnClickListener {
            playRandomSound()
        }
        wrong1 = findViewById(R.id.wrong1)
        wrong2 = findViewById(R.id.wrong2)
        correct = findViewById(R.id.correct)

        val imageLobo: ImageView = findViewById(R.id.imagelobo)
        val imageserpiente: ImageView = findViewById(R.id.imageserpiente)
        val imageAgila: ImageView = findViewById(R.id.imageagila)

        imageLobo.setOnClickListener {
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
            1 -> R.raw.howl1
            2 -> R.raw.howl2
            else -> R.raw.howl3
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
