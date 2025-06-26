package com.example.mobile_client_app.util

val numericRegex = Regex("[^0-9]")

fun phoneNumberVerification(input: String): String {
    val stripped = numericRegex.replace(input, "")
    return if (stripped.length >= 10) {
        stripped.substring(0..9)
    } else {
        stripped
    }
}

fun validName(name: String): String? {
    if (name.length >= 10) return null
    if (name.all { it.isLetter() })
        return name
    return null
}

fun validNumber(number: String): String? {
    val stripped = numericRegex.replace(number, "")
    return stripped
}