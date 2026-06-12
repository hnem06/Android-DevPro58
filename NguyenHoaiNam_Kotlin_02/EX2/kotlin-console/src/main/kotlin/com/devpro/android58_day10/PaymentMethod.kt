package com.devpro.android58_day10

//Tính đa hình
interface PaymentMethod {
    fun pay(amount: Double): Boolean
}

class CardPayment: PaymentMethod {
    override fun pay(amount: Double): Boolean {
        if (amount <= 0) {
            println("Invalid amount: $amount")
            return false
        }

        println("Processing card payment of $$amount")
        // Giả lập thanh toán thành công
        return true
    }
}

class CashPayment: PaymentMethod{
    override fun pay(amount: Double): Boolean {
        if (amount <= 0) {
            println("Invalid amount: $amount")
            return false
        }

        println("Processing cash payment of $$amount")
        // Giả lập thanh toán thành công
        return true
    }
}

