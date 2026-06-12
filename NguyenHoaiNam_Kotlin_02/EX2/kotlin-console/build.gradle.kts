plugins {
    kotlin("jvm") version "2.1.21"
    application
}

application {
    mainClass.set("com.devpro.android58_day10.MainKt")
}

// Cho phép đọc input từ bàn phím khi chạy bằng Gradle
tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

dependencies {
    implementation(kotlin("stdlib"))
}
