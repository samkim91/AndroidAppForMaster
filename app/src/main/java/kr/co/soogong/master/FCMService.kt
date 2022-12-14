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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.usecase.auth.SaveFCMTokenUseCase
import kr.co.soogong.master.presentation.uihelper.main.MainActivityHelper
import kr.co.soogong.master.presentation.uihelper.requirment.action.ViewRequirementActivityHelper
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class FCMService : FirebaseMessagingService() {
    @Inject
    lateinit var saveFCMTokenUseCase: SaveFCMTokenUseCase

    private val job = CoroutineScope(Job())

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.tag(TAG).d("onNewToken: $token")

        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        Timber.tag(TAG).d("sendRegistrationToServer: $token")

        job.launch {
            try {
                saveFCMTokenUseCase(token)
                Timber.tag(TAG).d("sendRegistrationToServer successfully: ")
            } catch (e: Exception) {
                Timber.tag(TAG).e("sendRegistrationToServer failed: $e")
            }
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.tag(TAG).d("onMessageReceived: from - ${remoteMessage.from}")
        if (remoteMessage.data.isNotEmpty()) {
            Timber.tag(TAG).d("onMessageReceived: data - ${remoteMessage.data}")
            sendNotification(remoteMessage)
        }

        // ???????????? notification ?????? ????????? ???, ???????????? ??????.. ????????? ????????????
        remoteMessage.notification?.let {
            Timber.tag(TAG).d("Message Notification Body: ${it.body}")
            sendNotification(remoteMessage)
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val notificationId = Random.nextInt()

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setColor(resources.getColor(R.color.c_22D47B, null))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentTitle(remoteMessage.data["Title"])
            .setContentText(remoteMessage.data["Body"])
            .setAutoCancel(true)
            .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://${packageName}/raw/soogong_noti_sound"))
            .setVibrate(longArrayOf(0, 500, 500, 500, 500))
            .setContentIntent(getPendingIntent(remoteMessage, notificationId))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        notificationManager?.notify(notificationId, builder.build())
    }

    private fun getPendingIntent(remoteMessage: RemoteMessage, notificationId: Int) =
        PendingIntent.getActivity(
            this,
            notificationId,
            when (remoteMessage.data["Destination"]) {
                "ViewRequirement" -> {
                    val requirementId = remoteMessage.data["RequirementId"]
                    Timber.tag(TAG).d("requirementId: $requirementId")
                    if (requirementId?.isNotEmpty()!!) {
                        ViewRequirementActivityHelper.getIntent(this, requirementId.toInt())
                    } else {
                        MainActivityHelper.getIntent(this)
                    }
                }
                else -> MainActivityHelper.getIntent(this)
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    companion object {
        private val TAG = FCMService::class.java.simpleName

        private const val BROKEN_CHANNEL_ID: String = "general_channel_4"
        private const val CHANNEL_ID: String = "general_channel_5"

        // ????????? ??????????????? ?????????, ?????? ?????? ????????? ????????? ?????? ????????? ?????? ????????? ???????????? ????????????.
        fun removeBrokenChannel(context: Context) {
            Timber.tag(TAG).d("removeBrokenChannel: $BROKEN_CHANNEL_ID")

            NotificationManagerCompat.from(context)
                .deleteNotificationChannel(BROKEN_CHANNEL_ID)
        }

        // ?????? ????????? ?????? ??? ????????? ??? ?????????, ?????? ????????? ?????? ???????????? ??? ??????. ?????? ????????? ??????!
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
                        Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://${context.packageName}/raw/soogong_noti_sound"),
                        Notification.AUDIO_ATTRIBUTES_DEFAULT
                    )
                    setVibrationEnabled(true)
                    setVibrationPattern(longArrayOf(0, 500, 500, 500, 500))
                }

            NotificationManagerCompat.from(context).createNotificationChannel(channel.build())
        }
    }
}