package com.example.mobile_client_app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform