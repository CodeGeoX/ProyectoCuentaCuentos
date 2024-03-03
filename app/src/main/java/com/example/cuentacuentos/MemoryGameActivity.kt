package com.example.cuentacuentos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.media.MediaPlayer

class MemoryGameActivity : AppCompatActivity() {
    private val cardBack = R.drawable.card_back
    private val images = listOf(R.drawable.agila, R.drawable.onewolf, R.drawable.serpiente, R.drawable.onepig)
    private var selectedCards = mutableListOf<Int>()
    private var cardViews = mutableListOf<ImageView>()
    private lateinit var correctSoundPlayer: MediaPlayer
    private lateinit var wrongSoundPlayer: MediaPlayer
    private var matchedCards = mutableSetOf<Int>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_game)

        sharedPreferences = getSharedPreferences("Juegos", Context.MODE_PRIVATE) // Obtiene SharedPreferences para el progreso del juego

        correctSoundPlayer = MediaPlayer.create(this, R.raw.correctsound) // Inicializa el reproductor de sonido para coincidencias correctas
        wrongSoundPlayer = MediaPlayer.create(this, R.raw.wrongsound) // Inicializa el reproductor de sonido para coincidencias incorrectas

        setupGame()
    }


    private fun setupGame() {
        cardViews = mutableListOf( // Inicializa la lista de vistas de cartas
            findViewById(R.id.card1),
            findViewById(R.id.card2),
            findViewById(R.id.card3),
            findViewById(R.id.card4),
            findViewById(R.id.card5),
            findViewById(R.id.card6),
            findViewById(R.id.card7),
            findViewById(R.id.card8)
        )

        val doubledImages = (images + images).shuffled() // Duplica y mezcla las imágenes

        cardViews.forEachIndexed { index, imageView -> // Asigna las imágenes a las cartas y configura sus listeners
            imageView.tag = doubledImages[index] // Establece la etiqueta de la vista de la carta como el ID de la imagen
            imageView.setImageDrawable(ContextCompat.getDrawable(this, cardBack)) // Establece el dorso de la carta como imagen
            imageView.setOnClickListener {
                onCardClicked(it) // Llama al método cuando se hace clic en una carta
            }
        }
    }

    // Método para gestionar el click de una carta
    private fun onCardClicked(view: View) {
        val cardIndex = cardViews.indexOf(view as ImageView) // Obtiene la carta clicada

        if (matchedCards.contains(cardIndex) || selectedCards.contains(cardIndex) || selectedCards.size >= 2) {
            return // Si la carta ya está emparejada, seleccionada o ya hay dos cartas seleccionadas, sale del método
        }

        view.setImageDrawable(ContextCompat.getDrawable(this, view.tag as Int))
        selectedCards.add(cardIndex)

        if (selectedCards.size == 2) {
            checkForMatch() // Comprueba si hay coincidencia cuando se han seleccionado dos cartas
        }
    }

    private fun checkForMatch() {
        val firstIndex = selectedCards[0] // Índice de las cartas seleccioandas
        val secondIndex = selectedCards[1]

        if (cardViews[firstIndex].tag == cardViews[secondIndex].tag) {
            correctSoundPlayer.start()
            matchedCards.add(firstIndex)
            matchedCards.add(secondIndex)

            if (matchedCards.size == cardViews.size) { // Si todas las cartas han sido emparejadas
                mostrarVictoriaDialog()
            }
        } else {
            wrongSoundPlayer.start() // Reproduce el sonido de error
            selectedCards.forEach { index ->
                cardViews[index].postDelayed({
                    cardViews[index].setImageDrawable(ContextCompat.getDrawable(this, cardBack))
                }, 500)
            }
        }
        selectedCards.clear() // Limpia la lista de cartas seleccionadas
    }


    private fun mostrarVictoriaDialog() {
        val hasWonBefore = sharedPreferences.getBoolean("victoria_juego_7", false) // Comprueba si el jugador ha ganado antes

        if (!hasWonBefore) { // si es la primera vez que gana se guarda en sharedpreferences
            sharedPreferences.edit().putBoolean("victoria_juego_7", true).apply()
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("¡Felicidades!")
        alertDialogBuilder.setMessage("¡Has ganado el juego!")
        alertDialogBuilder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
            val intent = Intent(this, JuegosActivity::class.java)
            startActivity(intent)
            finish()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
