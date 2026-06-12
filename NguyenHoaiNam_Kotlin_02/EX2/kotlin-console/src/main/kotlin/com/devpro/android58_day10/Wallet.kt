package com.devpro.android58_day10

//Đóng gói
class Wallet(initBalance:Double) {
    private var balance = initBalance
    var isEmpty: Boolean
        get() = balance <= 0.0
        set(value) {
            if (value) balance = 0.0
        }

    fun deposit(amount: Double) {
        if (amount > 0) balance += amount
    }

    fun getBalance(): Double {
        return balance
    }
}
