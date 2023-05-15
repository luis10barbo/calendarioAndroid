package com.luisbb.calendarapp.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.luisbb.calendarapp.R

@RequiresApi(Build.VERSION_CODES.O)

class NotificationHandler(private val activity: AppCompatActivity) {
    private val remindChannelId = "EVENT_REMIND_CHANNEL"
    private val warnChannelId = "EVENT_WARN_CHANNEL"


    init {
        setupChannels()
    }

    private fun setupChannels() {
        createRemindChannel()
        createWarnChannel()
    }

    private fun createNotificationChannel(channelId:String, channelName:String, channelDescription: String, importance: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channel = NotificationChannel(channelId, channelName, importance)
        channel.description = channelDescription

        val notificationManager = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createRemindChannel() {
        createNotificationChannel(
            remindChannelId,
            "Lembrar eventos",
            "Notificações acionadas ao chegar perto do horario do evento.",
            NotificationManager.IMPORTANCE_DEFAULT
        )
    }

    private fun createWarnChannel() {
        createNotificationChannel(warnChannelId, "Avisar eventos", "Notificações acionadas ao chegar no horario do evento.", NotificationManager.IMPORTANCE_MAX)
    }

    private fun createRemindNotification() {
        val builder = NotificationCompat.Builder(activity, remindChannelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Lembrete Evento")
            .setContentText("Calendario")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(activity)) {
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                activity.requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
            notify(1, builder.build())
        }
        builder.build()
    }

    private fun createWarnNotification() {
        val builder = NotificationCompat.Builder(activity, warnChannelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("AVISO Evento")
            .setContentText("Aqui esta comencando uma atividade")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(activity)) {
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                activity.requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
            notify(1, builder.build())
        }
        builder.build()
    }
}