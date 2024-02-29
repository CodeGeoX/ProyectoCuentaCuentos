package com.example.cuentacuentos

import android.media.AudioManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class CuentoActivity : AppCompatActivity() {
    private lateinit var videoView: VideoView
    private var isMuted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cuento_view)

        videoView = findViewById(R.id.videoView)
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.evilgoblin)
        videoView.setVideoURI(videoUri)

        videoView.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
            } else {
                videoView.start()
            }
        }

        videoView.start()
    }

    override fun onPause() {
        super.onPause()
        if (videoView.isPlaying) {
            videoView.pause()
        }
    }

    override fun onStop() {
        super.onStop()
        videoView.stopPlayback()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_video, menu)
        return true
    }

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
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleAudio() {
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        if (!isMuted) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
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
}
