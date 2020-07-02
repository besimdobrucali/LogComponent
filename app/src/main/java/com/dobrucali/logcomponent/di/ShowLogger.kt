package com.dobrucali.logcomponent.di

import android.util.Log
import com.dobrucali.logcomponent.component.ShowLogView
import com.dobrucali.logcomponent.di.ILogger

class ShowLogger : ILogger {

    var showLogView: ShowLogView? = null

    fun initializeView(showLogView: ShowLogView) {
        this.showLogView = showLogView
    }

    override fun v(msg: String) {
        showLogView?.addLog(msg, Log.VERBOSE)
    }

    override fun d(msg: String) {
        showLogView?.addLog(msg, Log.DEBUG)
    }

    override fun w(msg: String) {
        showLogView?.addLog(msg, Log.WARN)
    }

    override fun e(msg: String) {
        showLogView?.addLog(msg, Log.ERROR)
    }
}