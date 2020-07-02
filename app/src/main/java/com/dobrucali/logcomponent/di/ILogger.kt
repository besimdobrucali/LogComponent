package com.dobrucali.logcomponent.di

interface ILogger {
    /**
     * Log verbose
     * @param msg Log message
     */
    fun v(msg: String)

    /**
     * Log debug
     * @param msg Log message
     */
    fun d(msg: String)

    /**
     * Log warning
     * @param msg Log message
     */
    fun w(msg: String)

    /**
     * Log error
     * @param msg Log message
     */
    fun e(msg: String)
}