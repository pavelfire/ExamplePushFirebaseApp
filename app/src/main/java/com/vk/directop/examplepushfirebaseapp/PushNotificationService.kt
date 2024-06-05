package com.vk.directop.examplepushfirebaseapp

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService:FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        //Update our server
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        //Respond to received message
    }
}