package kr.co.soogong.master

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kr.co.soogong.master.uihelper.main.MainActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.ViewRequirementActivityHelper
import timber.log.Timber

class SoogongMasterMessagingService : FirebaseMessagingService() {
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
        val pendingIntent =
            PendingIntent.getActivity(
                this,
                0,
                findDestination(
                    remoteMessage.data["Destination"],
                    remoteMessage.data["RequirementId"]
                ),
                PendingIntent.FLAG_ONE_SHOT
            )

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.drawable.ic_notification_icon)
            color = ContextCompat.getColor(this@SoogongMasterMessagingService, R.color.color_22D47B)
            setContentTitle(remoteMessage.data["Title"])
            setContentText(remoteMessage.data["Body"])
            setAutoCancel(true)
            setSound(defaultSoundUri)
            setContentIntent(pendingIntent)
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = getString(R.string.default_notification_channel_name)
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager?.createNotificationChannel(channel)
        }

        notificationManager?.notify(
            System.currentTimeMillis().toInt(),
            builder.build()
        )
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.tag(TAG).d("onNewToken: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        // Implement this method to send token to your app server.
        Timber.tag(TAG).d("sendRegistrationTokenToServer($token)")
    }

    private fun findDestination(destination: String?, requirementId: String?): Intent {
        return when (destination) {
            "ViewRequirement" -> {
                if (requirementId?.isNotEmpty()!!) {
                    ViewRequirementActivityHelper.getIntent(this, requirementId.toInt())
                } else {
                    MainActivityHelper.getIntent(this)
                }
            }
            else -> MainActivityHelper.getIntent(this)
        }
    }

    companion object {
        private const val TAG = "FCM"
    }
}