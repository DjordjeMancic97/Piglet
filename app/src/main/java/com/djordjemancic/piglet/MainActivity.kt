package com.djordjemancic.piglet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.djordjemancic.piglet.ui.authentication.AuthenticationActivity
import com.djordjemancic.piglet.ui.home.HomeActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        val intent = if (Firebase.auth.currentUser != null) Intent(this, HomeActivity::class.java) else Intent(this, AuthenticationActivity::class.java)
        startActivity(intent)
        finish()
    }
}