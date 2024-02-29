package com.example.cuentacuentos

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.media.MediaPlayer
import androidx.appcompat.app.AlertDialog

class MemoryGameActivity : AppCompatActivity() {
    private val cardBack = R.drawable.card_back
    private val images = listOf(R.drawable.agila, R.drawable.onewolf, R.drawable.serpiente, R.drawable.onepig) // Asume que tienes algunos drawables para las imágenes de las cartas.
    private var selectedCards = mutableListOf<Int>()
    private var cardViews = mutableListOf<ImageView>()
    private lateinit var correctSoundPlayer: MediaPlayer
    private lateinit var wrongSoundPlayer: MediaPlayer

    private var matchedCards = mutableSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_game)

        // Inicializa los reproductores de sonido
        correctSoundPlayer = MediaPlayer.create(this, R.raw.correctsound)
        wrongSoundPlayer = MediaPlayer.create(this, R.raw.wrongsound)

        setupGame()
    }

    private fun setupGame() {
        cardViews = mutableListOf(
            findViewById(R.id.card1),
            findViewById(R.id.card2),
            findViewById(R.id.card3),
            findViewById(R.id.card4),
            findViewById(R.id.card5),
            findViewById(R.id.card6),
            findViewById(R.id.card7),
            findViewById(R.id.card8)
        )

        // Duplica la lista de imágenes para asegurar que haya un par de cada una, luego baraja.
        val doubledImages = (images + images).shuffled()

        cardViews.forEachIndexed { index, imageView ->
            imageView.tag = doubledImages[index]
            imageView.setImageDrawable(ContextCompat.getDrawable(this, cardBack))
            imageView.setOnClickListener {
                onCardClicked(it)
            }
        }
    }

    private fun onCardClicked(view: View) {
        val cardIndex = cardViews.indexOf(view as ImageView)

        // Verifica si la carta ya está seleccionada o ya ha sido emparejada
        if (!matchedCards.contains(cardIndex) && !selectedCards.contains(cardIndex) && selectedCards.size < 2) {
            view.setImageDrawable(ContextCompat.getDrawable(this, view.tag as Int))
            selectedCards.add(cardIndex)

            if (selectedCards.size == 2) {
                checkForMatch()
            }
        }
    }


    private fun checkForMatch() {
        val firstIndex = selectedCards[0]
        val secondIndex = selectedCards[1]

        if (cardViews[firstIndex].tag == cardViews[secondIndex].tag) {
            // Match found
            correctSoundPlayer.start()
            matchedCards.add(firstIndex)
            matchedCards.add(secondIndex)
            if (matchedCards.size == cardViews.size) {
                // All cards are matched
                // Mostrar el mensaje de victoria
                mostrarVictoriaDialog()
            }
        } else {
            // No match, reset cards after a delay
            wrongSoundPlayer.start()
            cardViews.forEachIndexed { index, imageView ->
                if (!matchedCards.contains(index)) {
                    imageView.postDelayed({
                        if (!matchedCards.contains(index)) {
                            imageView.setImageDrawable(ContextCompat.getDrawable(this, cardBack))
                        }
                    }, 1000)
                }
            }
        }
        selectedCards.clear()
    }

    private fun mostrarVictoriaDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("¡Felicidades!")
        alertDialogBuilder.setMessage("¡Has ganado el juego!")
        alertDialogBuilder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
            // Aquí puedes agregar cualquier acción adicional que desees realizar después de ganar el juego
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}
