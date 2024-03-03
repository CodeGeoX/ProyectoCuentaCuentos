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
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.tresporcscatala)
        videoView.setVideoURI(videoUri)

        videoView.setOnPreparedListener { mediaPlayer ->
            videoSeekBar.max = videoView.duration
            updateSeekBar()
        }

        videoView.start()

        videoSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    videoView.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        videoView.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
            } else {
                videoView.start()
            }
        }
    }

    private fun updateSeekBar() {
        if (!isFinishing) {
            videoSeekBar.progress = videoView.currentPosition
            handler.postDelayed({ updateSeekBar() }, 100)
        }
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
            R.id.action_language -> {
                showLanguageDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLanguageDialog() {
        val languages = arrayOf("Catalan", "Spanish", "Arabic")
        val videoUrls = arrayOf(
            R.raw.tresporcscatala,
            R.raw.tresporcscast,
            R.raw.tresporcsarab
        )

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Language")
        builder.setItems(languages) { dialog, which ->
            val videoUri = Uri.parse("android.resource://" + packageName + "/" + videoUrls[which])
            videoView.setVideoURI(videoUri)
            videoView.start()
        }
        val dialog = builder.create()
        dialog.show()
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

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}