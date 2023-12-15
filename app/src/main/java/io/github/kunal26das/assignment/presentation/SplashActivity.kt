package io.github.kunal26das.assignment.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import io.github.kunal26das.assignment.Constants
import io.github.kunal26das.assignment.work.ScreenshotsWorker

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val mainActivity = registerForActivityResult(MainActivity.Contract()) {}
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                mainActivity.launch(null)
                startScreenshotService()
                ScreenshotsWorker.enqueue(this)
            }
            finish()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermission.launch(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun startScreenshotService() {
        val serviceIntent = Intent(this, ScreenshotService::class.java)
        startService(serviceIntent)
    }
}