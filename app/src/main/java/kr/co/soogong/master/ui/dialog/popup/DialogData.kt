package kr.co.soogong.master.ui.dialog.popup

import android.content.Context
import androidx.annotation.ColorInt
import kr.co.soogong.master.R

data class DialogData(
    var title: String?,
    @ColorInt var titleTxtColor: Int,
    var description: String?,
    @ColorInt var descriptionTxtColor: Int,
    var positiveBtnText: String?,
    @ColorInt var positiveBtnTextColor: Int,
    var negativeBtnText: String?,
    @ColorInt var negativeBtnTextColor: Int
) {
    companion object {
        fun cancelDialogData(context: Context) =
            DialogData(
                "견적을 내기 어려우신가요?", context.getColor(R.color.text_basic_color),
                "해당 문의가 삭제됩니다.", context.getColor(R.color.text_alert_color),
                "네", context.getColor(R.color.text_basic_color),
                "아니요", context.getColor(R.color.text_basic_color)
            )

        fun cancelSendingTransmissionDialogData(context: Context) =
            DialogData(
                "견적서 작성을 취소하시겠어요?", context.getColor(R.color.text_basic_color),
                "이 화면에서 나가면 작성했던 내용이\n모두 삭제됩니다.", context.getColor(R.color.text_alert_color),
                "네", context.getColor(R.color.text_basic_color),
                "아니요", context.getColor(R.color.text_basic_color)
            )

        fun callDialogData(context: Context) =
            DialogData(
                "고객과 전화 연결 하시겠어요?", context.getColor(R.color.text_basic_color),
                null, context.getColor(R.color.text_alert_color),
                "네", context.getColor(R.color.text_basic_color),
                "아니요", context.getColor(R.color.text_basic_color)
            )

        fun cancelSignUpDialogData(context: Context) =
            DialogData(
                "가입신청을 그만두지 마세요\n지금 가입하면\n문의 수신, 견적 발송 무료!",
                context.getColor(R.color.text_basic_color),
                "이 화면에서 나가면 작성했던 내용이\n모두 삭제됩니다.",
                context.getColor(R.color.text_alert_color),
                "가입 할래요",
                context.getColor(R.color.text_basic_color),
                "그만 할래요",
                context.getColor(R.color.text_hint_color)
            )

        fun notificationDialogData(context: Context) =
            DialogData(
                "수공 마스터 앱 혜택 알림 받기",
                context.getColor(R.color.text_basic_color),
                "\'수공 마스터 앱\'에서 제공하는\n유용한 혜택 정보에 대한 알림을\n받으시겠어요?",
                context.getColor(R.color.text_alert_color),
                "네 받을래요!",
                context.getColor(R.color.text_basic_color),
                "아니요",
                context.getColor(R.color.text_hint_color)
            )

        fun askingFillProfileDialogData(context: Context) =
            DialogData(
                "문의를 처리하려면\n필수 정보 등록이 필요해요.\n지금 등록하시겠어요?", context.getColor(R.color.text_basic_color),
                null, context.getColor(R.color.text_alert_color),
                "네", context.getColor(R.color.text_basic_color),
                "아니요", context.getColor(R.color.text_basic_color)
            )

        fun askingDeletePortfolioDialogData(context: Context) =
            DialogData(
                "포트폴리오를 삭제하시겠어요?", context.getColor(R.color.text_basic_color),
                null, context.getColor(R.color.text_alert_color),
                "네", context.getColor(R.color.text_basic_color),
                "아니요", context.getColor(R.color.text_basic_color)
            )

        fun askingDeletePriceByProjectDialogData(context: Context) =
            DialogData(
                "시공 종류별 가격을 삭제하시겠어요?", context.getColor(R.color.text_basic_color),
                null, context.getColor(R.color.text_alert_color),
                "네", context.getColor(R.color.text_basic_color),
                "아니요", context.getColor(R.color.text_basic_color)
            )
    }
}
