package com.devpro.android58_day10

import com.devpro.android58_day10.classtype.data_class.Constants
import com.devpro.android58_day10.classtype.data_class.DemoEnum
import com.devpro.android58_day10.classtype.data_class.DemoSealed
import com.devpro.android58_day10.example.ex1.BankAccount
import com.devpro.android58_day10.utils.MyExtendFunction.isPrime
import com.devpro.android58_day10.utils.MyExtendFunction.openDoor

/**
 * Demo Kotlin cơ bản - Android58 Day10
 * Nhập xuất dữ liệu từ bàn phím
 */

// ========== MAIN ==========
fun main() {
    println("╔══════════════════════════════════════╗")
    println("║     DEMO KOTLIN CƠ BẢN - DAY 11      ║")
    println("╚══════════════════════════════════════╝")

//    val wallet = Wallet(100.0)
//
//    val dog = Dog("Buddy")
//    dog.makeSound()
//
//    val cardPayment = CardPayment()
//
//    val cashPayment = CashPayment()
//
//    val demoSealed = DemoSealed.Data("Hello, Sealed Class!")
//    when (demoSealed) {
//        is DemoSealed.Data -> println("Data value: ${demoSealed.value}")
//        is DemoSealed.Object -> println("This is an object")
//        is DemoSealed.Regular -> println("Regular number: ${demoSealed.number}")
//    }
//
//    val demoEnum = DemoEnum.B
//    when (demoEnum) {
//        DemoEnum.A -> println("Enum value: A")
//        DemoEnum.B -> println("Enum value: B")
//        DemoEnum.C -> println("Enum value: C")
//    }
//
//    println("Constants: USER_NAME = ${Constants.USER_NAME}, USER_AGE = ${Constants.USER_AGE}")


//    val demoCar = Car("Toyota", "Camry")
//    demoCar.start()
//    demoCar.openDoor()
//
//    val number = 17
//    println("$number is prime: ${number.isPrime()}")

    ex1()
}

fun ex1() {
    val bankAccount = BankAccount("HaiNe",-5.0)
    bankAccount.getBalance()

    bankAccount.deposit(100.0)
    bankAccount.getBalance()

    bankAccount.withdraw(30.0)
    bankAccount.getBalance()

    bankAccount.withdraw(100.0) // Thử rút nhiều hơn số dư
    bankAccount.getBalance()

}

fun ex2() {

}
