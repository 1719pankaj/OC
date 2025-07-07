package com.example.oc

import android.app.Application
import com.google.firebase.FirebaseApp

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //This inits Firebase, do not fuck with it or its Manifest entry.
        FirebaseApp.initializeApp(this)
    }
}