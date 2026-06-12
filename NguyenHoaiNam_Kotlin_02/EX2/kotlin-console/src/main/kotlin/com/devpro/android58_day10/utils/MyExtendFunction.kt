package com.devpro.android58_day10.utils

import com.devpro.android58_day10.Car

object MyExtendFunction {
    //Extend function - Hàm mở rộng
    fun Car.openDoor(){
        println("${this.getInfo()} Opening car door")
    }

    fun Int.isPrime(): Boolean {
        if (this <= 1) return false
        for (i in 2..this / 2) {
            if (this % i == 0) return false
        }
        return true
    }
}