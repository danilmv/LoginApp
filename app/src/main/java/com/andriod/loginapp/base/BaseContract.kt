package com.andriod.loginapp.base

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
        val delayedRun = { runnable: Runnable ->
            {
                Thread {
                    Thread.sleep(SLEEP_TIME)
                    runnable.run()
                }.start()
            }
        }
    }
}