package com.abolfazloskooii.videoplayer

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.PlayerView
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {

    lateinit var exo: PlayerView
    lateinit var timer: Timer
    var sec = 0
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timer = Timer()
        val video = findViewById<VideoView>(R.id.videoView)
        val time = findViewById<TextView>(R.id.timer)
        val time1 = findViewById<TextView>(R.id.timer1)
        exo = findViewById<PlayerView>(R.id.exo_player)

        timer.schedule(object : TimerTask(){
            override fun run() {
                runOnUiThread {
                    time.text = sec.toString()
                    time1.text = sec.toString()
                    sec++
                }
            }
        },1000,1000)

        video.setVideoURI(Uri.parse("https://up.7learn.com/market/experts/android.mp4"))
        video.setOnErrorListener { mp, what, extra ->
            Toast.makeText(this, extra.toString(), Toast.LENGTH_SHORT).show()
            true
        }

        video.setOnPreparedListener {
            video.start()
            var mediaController: MediaController = MediaController(this)
            mediaController.setMediaPlayer(video)
            video.setMediaController(mediaController)
            mediaController.setAnchorView(video)

        }
        exo()


    }

    fun exo() {

        val exoPlayer = ExoPlayer.Builder(this).build()
        val mediaItem = MediaItem.fromUri("https://up.7learn.com/market/experts/android.mp4")
        exoPlayer.setMediaItem(mediaItem)
        exo.player = exoPlayer
        exoPlayer.prepare()
        exoPlayer.play()
        if (exoPlayer.currentPosition == 1000L)
            timer.cancel()


    }
}