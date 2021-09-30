package com.andriod.loginapp.base

import android.os.Handler
import android.os.HandlerThread

private const val SLEEP_TIME = 1000L

abstract class BaseContract {
    enum class ViewState {
        IDLE, LOADING, COMPLETE
    }

    interface View {
        fun setState(state: ViewState)
    }

    interface Presenter {

        fun onAttach(view: View)
        fun onDetach()
    }

    companion object {
        private val handlerThread = HandlerThread("handlerThread").apply { isDaemon = true;start() }
        private val handler = Handler(handlerThread.looper)
        val delayedRun = { runnable: Runnable -> handler.postDelayed(runnable, SLEEP_TIME) }
    }
}