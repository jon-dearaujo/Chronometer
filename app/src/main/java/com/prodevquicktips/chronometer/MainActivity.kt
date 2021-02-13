package com.prodevquicktips.chronometer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.prodevquicktips.chronometer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var model: TimeViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProviders.of(this).get(TimeViewModel::class.java)
        binding = ActivityMainBinding.inflate(layoutInflater)
        Log.i("Activity", "model and binding done")
        setContentView(binding.root)

        setupButtons()
        setupTimeObserver()
        setupStateObserver()
    }

    private fun setupButtons() {
        binding.startButton.setOnClickListener { model.start() }
        binding.stopButton.setOnClickListener { model.stop() }
        binding.restartButton.setOnClickListener { model.restart() }
        Log.i("TimeViewModel", "buttons setup")
    }

    private fun setupTimeObserver() {
        val timeObserver = Observer<String> { time ->
            binding.timeText.text = time
        }

        model.timeText.observe(this, timeObserver)
        Log.i("TimeViewModel", "text observer setup")
    }

    private fun setupStateObserver() {
        val stateObserver = Observer<State> { state ->
            binding.startButton.isEnabled = state == State.STOPPED
            binding.stopButton.isEnabled = state == State.RUNNING
        }

        model.timerState.observe(this, stateObserver)
        Log.i("TimeViewModel", "state observer setup")
    }
}
