package com.devpro.android58_day10

// Tính trừu tượng
abstract class Vehicle(protected val brand: String, protected val model: String) {
    fun start() {
        println("Vehicle is starting")
    }

    abstract fun stop()
}

class Car (brand: String, model: String) : Vehicle(brand, model), PaymentMethod {
    override fun stop() {
        println("Car is stopping")
    }

    override fun pay(amount: Double): Boolean {
        if (amount <= 0) {
            println("Invalid amount: $amount")
            return false
        }

        println("Processing car payment of $$amount")
        // Giả lập thanh toán thành công
        return true
    }

    fun getInfo() = "$brand $model"
}