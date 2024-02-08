package com.example.cuentacuentos


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class JuegosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.juego_main)

        val game1: Button = findViewById(R.id.game1)
        game1.setOnClickListener {
            val intent = Intent(this, JuegoSeleccionCerdos::class.java)
            startActivity(intent)
        }

        val game2: Button = findViewById(R.id.game2)
        game2.setOnClickListener {
            val intent = Intent(this, JuegoCantidadCerdos::class.java)
            startActivity(intent)
        }
    }
}
