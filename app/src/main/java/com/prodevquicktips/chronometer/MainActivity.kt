package com.prodevquicktips.chronometer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.prodevquicktips.chronometer.parser.TimeParser

class MainActivity : AppCompatActivity() {

    private lateinit var model: TimeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProviders.of(this).get(TimeViewModel::class.java)

        setupButtons()
        setupTimeObserver()
        setupStateObserver()
    }

    private fun setupButtons() {
        findViewById<Button>(R.id.start_button).setOnClickListener { model.start() }
        findViewById<Button>(R.id.stop_button).setOnClickListener { model.stop() }
        findViewById<Button>(R.id.restart_button).setOnClickListener { model.restart() }
    }

    private fun setupTimeObserver() {
        val timeTv = findViewById<TextView>(R.id.time_tv)

        val timeObserver = Observer<String> { time ->
            timeTv.text = time
        }

        model.timeText.observe(this, timeObserver)
    }

    private fun setupStateObserver() {
        val start   = findViewById<Button>(R.id.start_button)
        val stop    = findViewById<Button>(R.id.stop_button)

        val stateObserver = Observer<State> { state ->
            start.isEnabled = state == State.STOPPED
            stop.isEnabled = state == State.RUNNING
        }

        model.timerState.observe(this, stateObserver)
    }

    private fun getInitialTime(): Long = 1000 * 60 * 20
}
