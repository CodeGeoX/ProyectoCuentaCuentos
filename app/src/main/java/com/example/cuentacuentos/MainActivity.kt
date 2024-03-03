package com.example.cuentacuentos

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import android.widget.VideoView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Dos botones que le permiten acceder al cuento o a los juegos
        val btncuento: ImageView = findViewById(R.id.btncuento)
        btncuento.setOnClickListener {
            val intent = Intent(this, CuentoActivity::class.java)
            startActivity(intent)
        }

        val btnJuegos: ImageView = findViewById(R.id.btnjuegos)
        btnJuegos.setOnClickListener {
            val intent = Intent(this, JuegosActivity::class.java)
            startActivity(intent)
        }
    }
    // se carga la toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}