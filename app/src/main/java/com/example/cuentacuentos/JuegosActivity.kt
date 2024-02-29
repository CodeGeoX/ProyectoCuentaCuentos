package com.example.cuentacuentos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class JuegosActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.juego_main)

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("Juegos", Context.MODE_PRIVATE)

        // Mostrar las estrellas correspondientes para cada juego
        mostrarEstrellaCompletado(R.id.stargame1empty, R.id.stargame1full, "victoria_juego_1")
        mostrarEstrellaCompletado(R.id.stargame2empty, R.id.stargame2full, "victoria_juego_2")
        mostrarEstrellaCompletado(R.id.stargame3empty, R.id.stargame3full, "victoria_juego_3")
        mostrarEstrellaCompletado(R.id.stargame4empty, R.id.stargame4full, "victoria_juego_4")
        mostrarEstrellaCompletado(R.id.stargame5empty, R.id.stargame5full, "victoria_juego_5")
        mostrarEstrellaCompletado(R.id.stargame6empty, R.id.stargame6full, "victoria_juego_6")
        mostrarEstrellaCompletado(R.id.stargame7empty, R.id.stargame7full, "victoria_juego_7")


        // Setear OnClickListener para cada juego
        val game1: Button = findViewById(R.id.game1)
        game1.setOnClickListener {
            val intent = Intent(this, PaintActivity::class.java)
            startActivity(intent)
        }

        val game2: Button = findViewById(R.id.game2)
        game2.setOnClickListener {
            val intent = Intent(this, JuegoCantidadCerdos::class.java)
            startActivity(intent)
        }

        val game3: Button = findViewById(R.id.game3)
        game3.setOnClickListener {
            val intent = Intent(this, JuegoCantidadLobos::class.java)
            startActivity(intent)
        }

        val game4: Button = findViewById(R.id.game4)
        game4.setOnClickListener {
            val intent = Intent(this, JuegoElegirDepredador::class.java)
            startActivity(intent)
        }
        val game5: Button = findViewById(R.id.game5)
        game5.setOnClickListener {
            val intent = Intent(this, JuegoSonidoCerdo::class.java)
            startActivity(intent)
        }

        val game6: Button = findViewById(R.id.game6)
        game6.setOnClickListener {
            val intent = Intent(this, JuegoSonidoLobo::class.java)
            startActivity(intent)
        }

        val game7: Button = findViewById(R.id.game7)
        game7.setOnClickListener {
            val intent = Intent(this, MemoryGameActivity::class.java)
            startActivity(intent)
        }
    }

    private fun mostrarEstrellaCompletado(idEstrellaEmpty: Int, idEstrellaFull: Int, claveSharedPreferences: String) {
        // Verificar si el juego ha sido completado en SharedPreferences
        val completado = sharedPreferences.getBoolean(claveSharedPreferences, false)
        // Mostrar estrella correspondiente
        if (completado) {
            findViewById<ImageView>(idEstrellaEmpty).visibility = View.GONE
            findViewById<ImageView>(idEstrellaFull).visibility = View.VISIBLE
        } else {
            findViewById<ImageView>(idEstrellaEmpty).visibility = View.VISIBLE
            findViewById<ImageView>(idEstrellaFull).visibility = View.GONE
        }
    }
}
