package com.example.cuentacuentos

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class JuegoCantidadCerdos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.juego_cantidad_cerdos)

        val imageTresCerdos: ImageView = findViewById(R.id.imageTresCerdos)
        val imageUnCerdo: ImageView = findViewById(R.id.imageUnCerdo)
        val imageDosCerdos: ImageView = findViewById(R.id.imageDosCerdos)

        imageTresCerdos.setOnClickListener {
            // Mostrar AlertDialog cuando el usuario acierte
            mostrarAlertDialog("¡Correcto!", "¡Has adivinado! Hay tres cerditos.")
        }

        imageUnCerdo.setOnClickListener {
            // Mostrar Toast cuando el usuario se equivoque
            mostrarToast("¡Incorrecto! No hay solo un cerdito.")
        }

        imageDosCerdos.setOnClickListener {
            // Mostrar Toast cuando el usuario se equivoque
            mostrarToast("¡Incorrecto! No hay solo dos cerditos.")
        }
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
