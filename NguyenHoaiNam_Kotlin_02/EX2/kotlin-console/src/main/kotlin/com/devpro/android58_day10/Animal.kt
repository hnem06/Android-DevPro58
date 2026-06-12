package com.devpro.android58_day10

// Tính kế thừa
open class Animal(val name: String) {
    open fun makeSound() {
        println("$name makes a sound")
    }
}

class Dog(name: String) : Animal(name) {

    override fun makeSound() {
        super.makeSound()
        println("$name says: Woof!")
    }
}
