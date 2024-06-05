package com.vk.directop.examplepushfirebaseapp.splash

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.vk.directop.examplepushfirebaseapp.ChatScreen
import com.vk.directop.examplepushfirebaseapp.ChatViewModel
import com.vk.directop.examplepushfirebaseapp.EnterTokenDialog
import com.vk.directop.examplepushfirebaseapp.R
import com.vk.directop.examplepushfirebaseapp.ui.theme.ExamplePushFirebaseAppTheme

class SplashActivity : BaseActivity() {

    private val viewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamplePushFirebaseAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state = viewModel.state
                    if (state.isEnteringToken) {
                        EnterTokenDialog(
                            token = state.remoteToken,
                            onTokenChange = viewModel::onRemoteTokenChange,
                            onSubmit = viewModel::onSubmitRemoteToken
                        )
                    } else {
                        ChatScreen(
                            messageText = state.messageText,
                            onMessageChange = viewModel::onMessageChange,
                            onMessageSend = {
                                viewModel.sendMessage(isBroadcast = false)
                            },
                            onMessageBroadcast = {
                                viewModel.sendMessage(isBroadcast = true)
                            }
                        )
                    }
                }
            }
        }

        if (!hasPermissions(this, *REQUIRED_PERMISSIONS))
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, ALL_PERMISSIONS)
        else
            Handler(Looper.getMainLooper()).postDelayed ({ startUi() }, 1000)
    }

    private fun hasPermissions(context: Context, vararg permissions: String): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    private fun startUi() {
        // navigate to main application
    }

    private fun finishActivity() {
        toast("You must grant all required permissions to continue")
        finish()
    }


    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)} passing\n      in a {@link RequestMultiplePermissions} object for the {@link ActivityResultContract} and\n      handling the result in the {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ALL_PERMISSIONS -> {
                if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) startUi()
                else finishActivity()
            }
        }
    }

    companion object {
        const val ALL_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    add(Manifest.permission.READ_MEDIA_IMAGES)
                    add(Manifest.permission.READ_MEDIA_VIDEO)
                    add(Manifest.permission.READ_MEDIA_AUDIO)
                    add(Manifest.permission.POST_NOTIFICATIONS)
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }.toTypedArray()
    }
}