package com.dobrucali.logcomponent.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dobrucali.logcomponent.adapter.LogAdapter

@SuppressLint("SetTextI18n")
@BindingAdapter("customLogFormatted")
fun TextView.setCustomLogFormatted(customLog: LogAdapter.CustomLog?) {
    customLog?.let {
        text = "${it.id} ${it.message}"
        val color = when (it.type) {
            Log.VERBOSE -> Color.GREEN
            Log.DEBUG -> Color.WHITE
            Log.WARN -> Color.BLUE
            Log.ERROR -> Color.RED
            else -> Color.WHITE
        }
        setTextColor(color)
    }
}