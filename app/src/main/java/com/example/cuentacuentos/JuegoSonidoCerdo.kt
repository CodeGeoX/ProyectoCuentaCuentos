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
import androidx.appcompat.widget.AppCompatImageView
import java.util.*

class JuegoSonidoCerdo : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var wrong1: AppCompatImageView
    private lateinit var wrong2: AppCompatImageView
    private lateinit var correct: AppCompatImageView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.juego_sonido_cerdo)
        // se prepara sharedpreferences
        sharedPreferences = getSharedPreferences("Juegos", Context.MODE_PRIVATE)
        // se prepara el sonido y si se presiona el botón hará que se escuche
        val playImageView: ImageView = findViewById(R.id.playsound)
        playImageView.setOnClickListener {
            playRandomSound()
        }
        wrong1 = findViewById(R.id.wrong1)
        wrong2 = findViewById(R.id.wrong2)
        correct = findViewById(R.id.correct)

        val imageCerdo: ImageView = findViewById(R.id.onepig)
        val imageserpiente: ImageView = findViewById(R.id.imageserpiente)
        val imageAgila: ImageView = findViewById(R.id.imageagila)

        imageCerdo.setOnClickListener {
            mostrarAlertDialog("Correcte!", "Ho fa el porquet")
            aciertoSound()
            ocultarImagenesIncorrectas()
            correct.visibility = View.VISIBLE
        }

        imageserpiente.setOnClickListener {
            mostrarToast("Incorrecte! Intenta-ho un altre cop")
            errorSound()
            wrong1.visibility = View.VISIBLE
            wrong2.visibility = View.GONE
            correct.visibility = View.GONE
        }

        imageAgila.setOnClickListener {
            mostrarToast("Incorrecte! Intenta-ho un altre cop")
            errorSound()
            wrong2.visibility = View.VISIBLE
            wrong1.visibility = View.GONE
            correct.visibility = View.GONE
        }
    }

    // Hay 3 sonidos para el cerdo, se selecciona uno aleatoriamente cada vez que se presiona el botón.
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
        alertDialogBuilder.setPositiveButton("Aceptar") { dialog, which ->
            //Cuando se presione el botón de aceptar al ganar se guarda la victoria en sharedpreferences
            sharedPreferences.edit().putBoolean("victoria_juego_5", true).apply()
            val intent = Intent(this, JuegosActivity::class.java)
            startActivity(intent)
            finish()
        }
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
