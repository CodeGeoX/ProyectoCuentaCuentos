package com.example.cuentacuentos

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.cuentacuentos.PaintView.Companion.colorList
import com.example.cuentacuentos.PaintView.Companion.currentBrush
import com.example.cuentacuentos.PaintView.Companion.pathList

class PaintActivity : AppCompatActivity() {

    companion object {
        var path = Path()
        var paintBrush = Paint()
    }
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paintlayout)
        supportActionBar?.hide()


        val sharedPreferences = getSharedPreferences("Juegos", MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("victoria_juego_1", true).apply()
        //Como el juego de pintar no tiene manera de ser completado se le asigna el estado de completo nada mas entrar en este

        context = this
        // prepara los colores
        val redBtn = findViewById<ImageButton>(R.id.redColor)
        val blueBtn = findViewById<ImageButton>(R.id.blueColor)
        val blackBtn = findViewById<ImageButton>(R.id.blackColor)
        val orangeBtn = findViewById<ImageButton>(R.id.orangeColor)
        val yellowBtn = findViewById<ImageButton>(R.id.yellowColor)
        val greenBtn = findViewById<ImageButton>(R.id.greenColor)
        val pinkBtn = findViewById<ImageButton>(R.id.pinkColor)
        val purpleBtn = findViewById<ImageButton>(R.id.purpleColor)
        val eraser = findViewById<ImageButton>(R.id.eraser)
        val backBtn = findViewById<ImageView>(R.id.back)
        backBtn.setOnClickListener {
            val intent = Intent(this, JuegosActivity::class.java)
            startActivity(intent)
        }
        redBtn.setOnClickListener {
            paintBrush.color = Color.RED
            currentColor(paintBrush.color)
        }
        blueBtn.setOnClickListener {
            paintBrush.color = Color.BLUE
            currentColor(paintBrush.color)
        }
        blackBtn.setOnClickListener {
            paintBrush.color = Color.BLACK
            currentColor(paintBrush.color)
        }
        greenBtn.setOnClickListener {
            paintBrush.color = Color.GREEN
            currentColor(paintBrush.color)
        }
        yellowBtn.setOnClickListener {
            paintBrush.color = Color.YELLOW
            currentColor(paintBrush.color)
        }
        orangeBtn.setOnClickListener {
            paintBrush.color = ContextCompat.getColor(context, R.color.orange)
            currentColor(paintBrush.color)
        }

        pinkBtn.setOnClickListener {
            paintBrush.color = ContextCompat.getColor(context, R.color.pink)
            currentColor(paintBrush.color)
        }

        purpleBtn.setOnClickListener {
            paintBrush.color = ContextCompat.getColor(context, R.color.purple)
            currentColor(paintBrush.color)
        }
        eraser.setOnClickListener {
            pathList.clear()
            colorList.clear()
            path.reset()
        }
    }
    private fun currentColor(color: Int) {
        currentBrush = color
        path = Path()
    }
}
