package com.vk.directop.examplepushfirebaseapp

data class ChatState(
    val isEnteringToken: Boolean = true,
    val remoteToken: String = "",
    val messageText: String = "",
)
