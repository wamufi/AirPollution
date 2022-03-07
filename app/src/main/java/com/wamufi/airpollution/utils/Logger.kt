package com.wamufi.airpollution.utils

import android.util.Log
import com.wamufi.airpollution.BuildConfig

object Logger {
    private const val TAG = "APLog"

    fun e(msg: String?) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, prepareLog(msg))
        }
    }

    fun w(msg: String?) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, prepareLog(msg))
        }
    }

    fun d(msg: String?) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, prepareLog(msg))
        }
    }

    fun i(msg: String?) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, prepareLog(msg))
        }
    }

    fun v(msg: String?) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, prepareLog(msg))
        }
    }

    private fun prepareLog(msg: String?): String {
        val ste = Thread.currentThread().stackTrace[4]
        val className = if (ste.fileName.endsWith(".kt")) {
            ste.fileName.replace(".kt", "")
        } else {
            ste.fileName.replace(".java", "")
        }

        return StringBuilder().append("[").append(className).append("::").append(ste.methodName).append("] ").append(msg).toString()
    }
}