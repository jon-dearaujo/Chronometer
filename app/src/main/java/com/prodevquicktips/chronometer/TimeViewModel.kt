package com.prodevquicktips.chronometer

import android.app.Application
import android.os.Handler
import android.util.Log
import androidx.lifecycle.*
import androidx.work.*
import com.prodevquicktips.chronometer.parser.TimeParser
import java.util.*
import java.util.concurrent.TimeUnit

class TimeViewModel: AndroidViewModel {
    val timeText: LiveData<String>
    val timerState: MutableLiveData<State> by lazy { MutableLiveData<State>() }
    private val time: MutableLiveData<Long> by lazy { MutableLiveData<Long>()}

    private lateinit var timer: Timer;
    private var initialTime: Long = 20 * 60 * 1000

    constructor(application: Application): super(application) {
        timeText = Transformations.map(time) {
            TimeParser.parse(it)
        }
        time.value = initialTime
        timerState.value = State.STOPPED
    }

    fun start() {
        setupWorker()
        timerState.value = State.RUNNING
    }

    fun stop() {
        tierDownWorker()
        timerState.value = State.STOPPED
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
        }, 1000, 1000)
    }

    private fun tierDownWorker() {
        timer.cancel()
    }
}