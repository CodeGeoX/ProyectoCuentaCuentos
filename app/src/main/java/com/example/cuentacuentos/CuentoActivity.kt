package com.example.cuentacuentos

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.cuentacuentos.R

class CuentoActivity : AppCompatActivity() {
    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cuento_view)

        videoView = findViewById(R.id.videoView)
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.evilgoblin)
        videoView.setVideoURI(videoUri)

        // Agregar OnClickListener al VideoView
        videoView.setOnClickListener {
            if (videoView.isPlaying) {
                // Si está reproduciendo, pausar
                videoView.pause()
            } else {
                // Si no está reproduciendo, iniciar la reproducción
                videoView.start()
            }
        }

        // Iniciar la reproducción del video
        videoView.start()
    }

    override fun onPause() {
        super.onPause()
        // Pausar el video cuando la actividad está en segundo plano
        if (videoView.isPlaying) {
            videoView.pause()
        }
    }

    override fun onStop() {
        super.onStop()
        // Detener la reproducción del video al salir de la actividad
        videoView.stopPlayback()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_video, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {

                onBackPressed()
                return true
            }
            R.id.action_audio -> {

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
