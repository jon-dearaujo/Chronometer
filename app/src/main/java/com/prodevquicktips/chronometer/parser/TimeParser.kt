package com.prodevquicktips.chronometer.parser

class TimeParser {
    companion object {
        public fun parse(time: Long): String {
            val seconds = time % 60000 // Get what remains after taking the minutes
            val minutes = time - seconds

            return "${buildMinutes(minutes)}:${buildSeconds(seconds)}"
        }

        private fun buildMinutes(milliseconds: Long): String {
            val minutes = milliseconds / 60000
            return if (minutes >= 10) minutes.toString() else "0${minutes}"
        }

        private fun buildSeconds(milliseconds: Long): String {
            val seconds = milliseconds / 1000
            return if (seconds == 60L) "00" else if (seconds >= 10) seconds.toString() else "0${seconds}"
        }
    }
}