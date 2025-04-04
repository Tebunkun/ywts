package com.example.ywts22b1num7184.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.ywts22b1num7184.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notification = NotificationCompat.Builder(context, "word_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Өнөөдрийн үг!")
            .setContentText("Шинэ үг сурцгаая!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val manager = ContextCompat.getSystemService(context, NotificationManager::class.java)
        manager?.notify(1, notification)
    }
}
