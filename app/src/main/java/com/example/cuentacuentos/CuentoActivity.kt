package com.example.cuentacuentos

import android.media.AudioManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class CuentoActivity : AppCompatActivity() {
    private lateinit var videoView: VideoView
    private lateinit var videoSeekBar: SeekBar
    private var isMuted = false
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cuento_view)

        videoView = findViewById(R.id.videoView)
        videoSeekBar = findViewById(R.id.videoSeekBar)

        // Establece url de video predeterminada
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.tresporcscatala)
        videoView.setVideoURI(videoUri)

        videoView.setOnPreparedListener { mediaPlayer ->
            videoSeekBar.max = videoView.duration // Prepara la barra de progreso para el vídeo
            updateSeekBar()
        }

        videoView.start() // Inicia el video

        videoSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    videoView.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Listener para pausar y reanudar el vídeo
        videoView.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
            } else {
                videoView.start()
            }
        }
    }

    // Método para actualizar la barra de progreso del video
    private fun updateSeekBar() {
        if (!isFinishing) {
            videoSeekBar.progress = videoView.currentPosition
            handler.postDelayed({ updateSeekBar() }, 100)
        }
    }

    override fun onPause() {
        super.onPause()
        if (videoView.isPlaying) {
            videoView.pause() // Pausar el video si está en marcha
        }
    }

    override fun onStop() {
        super.onStop()
        videoView.stopPlayback()
    }

    // Método para preparar el menú de la toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_video, menu)
        return true
    }

    // Opciones de la toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                onBackPressed()
                true
            }
            R.id.action_audio -> {
                toggleAudio()
                true
            }
            R.id.action_language -> {
                showLanguageDialog() // Opciones para cambiar el idioma
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLanguageDialog() {
        val languages = arrayOf("Català", "Castellà", "Àrab")
        val videoUrls = arrayOf(
            R.raw.tresporcscatala,
            R.raw.tresporcscast,
            R.raw.tresporcsarab
        )

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona l'idioma")
        builder.setItems(languages) { dialog, which ->
            val videoUri = Uri.parse("android.resource://" + packageName + "/" + videoUrls[which])
            videoView.setVideoURI(videoUri)
            videoView.start()
        }
        val dialog = builder.create()
        dialog.show()
    }

    // Método para activar o desactivar el audio del movil
    private fun toggleAudio() {
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        if (!isMuted) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
            // para gestionar el mute del audio se baja el volumen del dispositivo al 0
            isMuted = true
        } else {
            audioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0
            )
            isMuted = false
        }
    }

    // Método que se ejecuta cuando la actividad se acaba
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
