package com.prodevquicktips.chronometer

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class TimeWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    companion object {
        const val KEY = "TIME"
    }
    override fun doWork(): Result {
        val time = inputData.getLong(KEY, 0)
        return Result.success(workDataOf(KEY to (time - 1000)))
    }
}