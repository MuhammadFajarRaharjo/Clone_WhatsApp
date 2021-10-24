package com.belajar.clonwhatsapp.utils

sealed class Screen(val route: String) {
    object Chat: Screen("whats_app_chat")
    object Status: Screen("whats_app_status")
    object Call: Screen("whats_app_telephone")

    object Message: Screen("message")
}
