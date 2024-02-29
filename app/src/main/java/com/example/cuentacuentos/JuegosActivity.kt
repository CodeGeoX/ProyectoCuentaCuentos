package com.example.cuentacuentos


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class JuegosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.juego_main)


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
}
