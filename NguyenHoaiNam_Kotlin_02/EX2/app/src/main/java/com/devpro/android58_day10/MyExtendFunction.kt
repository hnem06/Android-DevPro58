package com.devpro.android58_day10

import android.view.View

object MyExtendFunction {

    fun View.hide(){
        this.visibility = View.GONE
    }

    fun View.disable(opacity:Float = 0.5f){
        this.isEnabled = false
        this.alpha = opacity
    }
}