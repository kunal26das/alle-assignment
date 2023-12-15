package io.github.kunal26das.assignment.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import io.github.kunal26das.assignment.R

class ScreenshotService : Service() {

    private val screenshotObserver by lazy { ScreenshotObserver(this) }

    override fun onCreate() {
        super.onCreate()
        observeScreenshot()
        startForegroundService()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun observeScreenshot() {
        contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true,
            screenshotObserver,
        )
    }

    private fun startForegroundService() {
        createNotificationChannel()

        val notification = NotificationCompat.Builder(this, CHANNEL)
            .setContentTitle("Screenshot Service")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(false)
            .build()

        startForeground(1, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL,
                CHANNEL,
                NotificationManager.IMPORTANCE_NONE
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        contentResolver.unregisterContentObserver(screenshotObserver)
    }

    companion object {
        private const val CHANNEL = "Screenshot Service Channel"
    }
}