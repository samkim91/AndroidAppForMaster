package kr.co.soogong.master

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kr.co.soogong.master.uiinterface.main.MainActivityHelper
import timber.log.Timber

class SoogongMasterMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.tag(TAG).d("onMessageReceived: from - ${remoteMessage.from}")
        if (remoteMessage.data.isNotEmpty()) {
            Timber.tag(TAG).d("onMessageReceived: data - ${remoteMessage.data}")
            sendNotification(remoteMessage.data["title"], remoteMessage.data["body"])
        }

        remoteMessage.notification?.let {
            Timber.tag(TAG).d("Message Notification Body: ${it.body}")
            sendNotification(it.title, it.body)
        }
    }

    private fun sendNotification(messageTitle: String?, messageBody: String?) {
        val intent = MainActivityHelper.getIntent(this)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle(messageTitle)
            setContentText(messageBody)
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
        // TODO: Implement this method to send token to your app server.
        Timber.tag(TAG).d("sendRegistrationTokenToServer($token)")
    }

    companion object {
        private const val TAG = "FCM"
    }
}