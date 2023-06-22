package com.scotiabank

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ScotiaBankApplication: Application() {
// HiltAndroidApp annotation marks this class as the entry point for Hilt dependency injection.

    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this)

        // Initialize the Fresco library for handling image loading and caching.
        // Fresco is an open-source image loading library developed by Facebook.
    }

}