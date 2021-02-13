package com.prodevquicktips.chronometer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

import com.prodevquicktips.chronometer.parser.TimeParser
import java.util.Timer
import java.util.TimerTask

class TimeViewModel: AndroidViewModel {
    val timeText: LiveData<String>
    val timerState: MutableLiveData<State> by lazy { MutableLiveData<State>() }
    private val time: MutableLiveData<Long> by lazy { MutableLiveData<Long>()}

    private lateinit var timer: Timer;
    private var initialTime: Long = 20 * 60 * 1000

    constructor(application: Application): super(application) {
        timeText = Transformations.map(time) {numericTime ->
            TimeParser.parse(numericTime)
        }
        time.value = initialTime
        timerState.value = State.STOPPED
        Log.i("TimeViewModel", "TimeViewModel started")
    }

    fun start() {
        setupWorker()
        timerState.value = State.RUNNING
        Log.i("TimeViewModel", "TimeViewModel start invoked")
    }

    fun stop() {
        tierDownWorker()
        timerState.value = State.STOPPED
        Log.i("TimeViewModel", "TimeViewModel stop invoked")
    }

    fun restart() {
        time.value = initialTime
        if (timerState.value == State.RUNNING) {
            tierDownWorker()
            setupWorker()
        }
    }

    private fun setupWorker() {
        timer = Timer()
        timer.scheduleAtFixedRate(object: TimerTask() {
            override fun run() {
                time.postValue(time.value!!.minus(1000))
                Log.i("TimeViewModel", "Current value: ${time.value}")
            }
        }, 0, 1000)
    }

    private fun tierDownWorker() {
        timer.cancel()
    }
}