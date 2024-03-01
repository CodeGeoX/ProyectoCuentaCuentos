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

        sharedPreferences = getSharedPreferences("Juegos", Context.MODE_PRIVATE)

        aciertoSound = MediaPlayer.create(this, R.raw.correctsound)
        errorSound = MediaPlayer.create(this, R.raw.wrongsound)

        val imageTresLobos: ImageView = findViewById(R.id.ImageThreeWolves)
        val imageUnLobo: ImageView = findViewById(R.id.ImageOneWolf)
        val imageDosLobos: ImageView = findViewById(R.id.ImageTwoWolves)
        wrong1 = findViewById(R.id.wrong1)
        wrong2 = findViewById(R.id.wrong2)
        correct = findViewById(R.id.correct)

        imageUnLobo.setOnClickListener {
            mostrarAlertDialog("Correcte!", "Només hi ha un llop.")
            aciertoSound.start()
            wrong1.visibility = View.GONE
            wrong2.visibility = View.GONE
            correct.visibility = View.VISIBLE
        }

        imageTresLobos.setOnClickListener {
            mostrarToast("Uh-Oh Intenta-ho un altre cop")
            errorSound.start()
            wrong1.visibility = View.VISIBLE
            wrong2.visibility = View.GONE
            correct.visibility = View.GONE
        }

        imageDosLobos.setOnClickListener {
            mostrarToast("Uh-Oh Intenta-ho un altre cop")
            errorSound.start()
            wrong2.visibility = View.VISIBLE
            wrong1.visibility = View.GONE
            correct.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        aciertoSound.release()
        errorSound.release()
    }

    private fun mostrarAlertDialog(titulo: String, mensaje: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(titulo)
        alertDialogBuilder.setMessage(mensaje)
        alertDialogBuilder.setPositiveButton("Aceptar") { dialog, _ ->
            sharedPreferences.edit().putBoolean("victoria_juego_3", true).apply()
            val intent = Intent(this, JuegosActivity::class.java)
            startActivity(intent)
            finish()
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
