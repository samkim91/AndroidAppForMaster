package kr.co.soogong.master

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kr.co.soogong.master.uihelper.main.MainActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.ViewRequirementActivityHelper
import timber.log.Timber

class SoogongMasterMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.tag(TAG).d("onNewToken: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.tag(TAG).d("onMessageReceived: from - ${remoteMessage.from}")
        if (remoteMessage.data.isNotEmpty()) {
            Timber.tag(TAG).d("onMessageReceived: data - ${remoteMessage.data}")
            sendNotification(remoteMessage)
        }

        // 서버에서 notification 으로 보냈을 때, 처리하는 로직.. 현재는 미사용중
        remoteMessage.notification?.let {
            Timber.tag(TAG).d("Message Notification Body: ${it.body}")
            sendNotification(remoteMessage)
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val builder =
            NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                .apply {
                    setSmallIcon(R.drawable.ic_notification_icon)
                    color = ContextCompat.getColor(
                        this@SoogongMasterMessagingService,
                        R.color.color_22D47B
                    )
                    priority = NotificationCompat.PRIORITY_HIGH
                    setContentTitle(remoteMessage.data["Title"])
                    setContentText(remoteMessage.data["Body"])
                    setAutoCancel(true)
                    setSound(getAlertSound(remoteMessage))
                    setDefaults(Notification.DEFAULT_VIBRATE)
                    setContentIntent(getPendingIntent(remoteMessage))
                }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                getString(R.string.default_notification_channel_id),
                getString(R.string.default_notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            ).run {
                notificationManager?.createNotificationChannel(this)
            }
        }

        notificationManager?.notify(System.currentTimeMillis().toInt(), builder.build())
    }

    private fun getAlertSound(remoteMessage: RemoteMessage) =
        when (remoteMessage.data["SoundType"]) {
            "CUSTOM" -> {
                Uri.parse("android.resource://${packageName}/" + R.raw.soogong_alert_sound)
            }
            else -> {
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            }
        }


    private fun getPendingIntent(remoteMessage: RemoteMessage) =
        PendingIntent.getActivity(
            this,
            0,
            when (remoteMessage.data["Destination"]) {
                "ViewRequirement" -> {
                    val requirementId = remoteMessage.data["RequirementId"]
                    if (requirementId?.isNotEmpty()!!) {
                        ViewRequirementActivityHelper.getIntent(this, requirementId.toInt())
                    } else {
                        MainActivityHelper.getIntent(this)
                    }
                }
                else -> MainActivityHelper.getIntent(this)
            },
            PendingIntent.FLAG_ONE_SHOT
        )

    companion object {
        private const val TAG = "FCM"
    }
}