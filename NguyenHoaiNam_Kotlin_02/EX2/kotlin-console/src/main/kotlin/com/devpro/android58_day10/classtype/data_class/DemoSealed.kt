package com.devpro.android58_day10.classtype.data_class

sealed class DemoSealed {
    data class Data(val value: String) : DemoSealed()
    object Object : DemoSealed()
    class Regular(val number: Int) : DemoSealed()
}

enum class DemoEnum {
    A, B, C
}