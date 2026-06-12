package com.devpro.android58_day10.example.ex2

interface NotificationSender {
    fun sendNotification(user: User, message: String) {
        println("Email Sender: $message by $user")
    }
}