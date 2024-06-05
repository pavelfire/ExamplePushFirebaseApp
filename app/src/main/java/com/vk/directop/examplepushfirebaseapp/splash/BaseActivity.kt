package com.vk.directop.examplepushfirebaseapp.splash

import android.widget.Toast
import androidx.activity.ComponentActivity

open class BaseActivity: ComponentActivity() {

    fun toast(s: String){
        Toast.makeText(
            this,
            s,
            Toast.LENGTH_LONG
        ).show()
    }

}
