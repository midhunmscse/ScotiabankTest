package com.scotiabank.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class ScopedActivity : AppCompatActivity(), CoroutineScope {

    // Job to manage coroutines within the activity
    private lateinit var job: Job

    // CoroutineContext for the activity's scope
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    // Called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Set the default night mode to MODE_NIGHT_NO to disable night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Initialize the job for coroutines
        job = Job()
    }

    // Called when the activity's content view is set
    override fun setContentView(layoutResID: Int) {

        super.setContentView(layoutResID)

        // Bind UI components within the activity
        bindUI()
    }

    // Called when the activity is destroyed
    override fun onDestroy() {
        super.onDestroy()

        // Cancel the job to cancel ongoing coroutines
        job.cancel()
    }

    // Method to bind UI components and start coroutines
    protected open fun bindUI(): Job = launch { }
}

