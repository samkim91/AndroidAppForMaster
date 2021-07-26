package kr.co.soogong.master.uihelper.profile

import android.content.*
import android.net.Uri
import kr.co.soogong.master.R
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.utility.extension.toast

object RequestReviewHelper {
    private const val KAKAO = "com.kakao.talk"
    private const val KAKAO_MARKET_URL = "market://details?id=com.kakao.talk"

    fun requestReviewBySharing(context: Context, masterUid: String?, masterName: String?, wayOfRequesting: String) {
        if (wayOfRequesting == "링크만 복사") {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText(
                "리뷰 요청하기 링크",
                HttpContract.REQUEST_REVIEW_BY_SHARING + masterUid
            )
            clipboard.setPrimaryClip(clip)
            context.toast(context.getString(R.string.link_copied_successfully))
        } else {
            try {
                context.startActivity(getIntent(context, masterName, wayOfRequesting))
            } catch (e: ActivityNotFoundException) {
                when(wayOfRequesting) {
                    "카카오톡으로 공유하기" -> context.startActivity(
                        kakaoActivityNotFound()
                    )
                    else -> context.toast(context.getString(R.string.request_review_by_sharing_failed))
                }
            }
        }
    }

    private fun getIntent(context: Context, masterUid: String?, masterName: String?, wayOfRequesting: String): Intent {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                context.getString(
                    R.string.request_review_template,
                    masterName
                ) + "\n" + HttpContract.REQUEST_REVIEW_BY_SHARING + masterUid
            )
        }

        return when (wayOfRequesting) {
            "메시지로 공유하기" -> intent.apply {
                action = Intent.ACTION_VIEW
                type = "vnd.android-dir/mms-sms"
            }
            "카카오톡으로 공유하기" -> intent.apply {
                setPackage(KAKAO)
            }
            else -> Intent.createChooser(
                intent, null
            )
        }
    }

    private fun kakaoActivityNotFound() = Intent(Intent.ACTION_VIEW, Uri.parse(KAKAO_MARKET_URL))
}
