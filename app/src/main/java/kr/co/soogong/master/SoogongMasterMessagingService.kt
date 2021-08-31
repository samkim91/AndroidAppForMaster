package kr.co.soogong.master

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kr.co.soogong.master.uihelper.main.MainActivityHelper
import kr.co.soogong.master.uihelper.requirment.action.ViewRequirementActivityHelper
import timber.log.Timber
import kotlin.random.Random

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
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setColor(resources.getColor(R.color.color_22D47B, null))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentTitle(remoteMessage.data["Title"])
            .setContentText(remoteMessage.data["Body"])
            .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://${packageName}/raw/soogong_alert_sound"))
            .setVibrate(longArrayOf(0, 500, 500, 500, 500))
            .setContentIntent(getPendingIntent(remoteMessage))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        notificationManager?.notify(Random.nextInt(), builder.build())
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

        private const val BROKEN_CHANNEL_ID: String = "general_channel_3"
        private const val CHANNEL_ID: String = "general_channel_4"

        // 노티에 변경사항이 생기면, 기존 노티 채널을 삭제한 뒤에 새로운 노티 채널을 추가해야 적용된다.
        fun removeBrokenChannel(context: Context) {
            Timber.tag(TAG).d("removeBrokenChannel: $BROKEN_CHANNEL_ID")

            NotificationManagerCompat.from(context)
                .deleteNotificationChannel(BROKEN_CHANNEL_ID)
        }

        // 노티 채널은 여러 개 사용할 수 있는데, 이를 상황에 따라 지정해줄 수 있다. 추후 다양화 필요!
        fun initNotificationChannel(context: Context) {
            Timber.tag(TAG).d("initNotificationChannel: $CHANNEL_ID")

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
            val channel = NotificationChannelCompat.Builder(
                CHANNEL_ID,
                NotificationManagerCompat.IMPORTANCE_HIGH
            )
                .apply {
                    setName(context.getString(R.string.default_notification_channel_id))
                    setDescription(context.getString(R.string.default_notification_channel_name))
                    setSound(
                        Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://${context.packageName}/raw/soogong_alert_sound"),
                        Notification.AUDIO_ATTRIBUTES_DEFAULT
                    )
                    setVibrationEnabled(true)
                    setVibrationPattern(longArrayOf(0, 500, 500, 500, 500))
                }

            NotificationManagerCompat.from(context).createNotificationChannel(channel.build())
        }
    }
}