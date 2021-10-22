package kr.co.soogong.master.ui.dialog.popup

import android.content.Context
import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.R

@Parcelize
data class DialogData(
    val title: String?,
    @ColorInt val titleTxtColor: Int,
    val description: String?,
    @ColorInt val descriptionTxtColor: Int,
    val positiveBtnText: String?,
    @ColorInt val positiveBtnTextColor: Int,
    val negativeBtnText: String?,
    @ColorInt val negativeBtnTextColor: Int,
) : Parcelable {
    companion object {
        fun getUpdatingAppMandatory(context: Context) =
            DialogData(
                "앱을 사용하시려면 업데이트 해주세요.", context.getColor(R.color.text_basic_color),
                null, context.getColor(R.color.text_alert_color),
                "확인", context.getColor(R.color.text_basic_color),
                null, context.getColor(R.color.text_basic_color)
            )

        fun getUpdatingAppRecommended(context: Context) =
            DialogData(
                "앱 업데이트를 통해 개선된 기능과 새로운 서비스를 이용해 보세요.", context.getColor(R.color.text_basic_color),
                null, context.getColor(R.color.text_alert_color),
                "확인", context.getColor(R.color.text_basic_color),
                "취소", context.getColor(R.color.text_basic_color)
            )

        fun getRefuseEstimateDialogData(context: Context) =
            DialogData(
                "견적을 내기 어려우신가요?", context.getColor(R.color.text_basic_color),
                "해당 문의가 삭제됩니다.", context.getColor(R.color.text_alert_color),
                "네", context.getColor(R.color.text_basic_color),
                "아니요", context.getColor(R.color.text_basic_color)
            )

        fun getConfirmRepairDoneDialogData(context: Context) =
            DialogData(
                "시공을 완료 하셨나요?",
                context.getColor(R.color.text_basic_color),
                "고객님과 합의되었을 때만 완료처리를\n해주세요. 완료처리를 하면 고객님께\n자동으로 리뷰요청을 하게 됩니다.",
                context.getColor(R.color.text_alert_color),
                "네",
                context.getColor(R.color.text_basic_color),
                "아니요",
                context.getColor(R.color.text_basic_color)
            )

        fun getRequestConsultAlertDialogData(context: Context) =
            DialogData(
                "고객님이 상세상담 통화를 요청하셨어요.\n고객님과 빠르게 통화하고 시공 가능성을\n높여보세요.",
                context.getColor(R.color.text_basic_color),
                "24시간동안 통화를 하지 않을 경우\n배정에 불이익이 있을 수 있어요.",
                context.getColor(R.color.text_alert_color),
                "확인",
                context.getColor(R.color.text_basic_color),
                null,
                context.getColor(R.color.text_basic_color)
            )

        fun getExpiredRequestConsultDialogData(context: Context) =
            DialogData(
                "이미 마감된 문의입니다.", context.getColor(R.color.text_basic_color),
                null, context.getColor(R.color.text_alert_color),
                "닫기", context.getColor(R.color.text_basic_color),
                null, context.getColor(R.color.text_basic_color)
            )

        fun getAcceptMeasureDialogData(context: Context) =
            DialogData(
                "현장 실측 요청을 수락하시겠어요?", context.getColor(R.color.text_basic_color),
                "수락 후 고객과 통화해\n정확한 시공 내용을 파악해주세요.", context.getColor(R.color.color_1FC472),
                "네", context.getColor(R.color.text_basic_color),
                "아니요", context.getColor(R.color.text_basic_color)
            )

        fun getRefuseMeasureDialogData(context: Context) =
            DialogData(
                "실측을 포기하시겠어요?", context.getColor(R.color.text_basic_color),
                null, context.getColor(R.color.color_1FC472),
                "네", context.getColor(R.color.text_basic_color),
                "아니요", context.getColor(R.color.text_basic_color)
            )

        fun getCancelSendingEstimationDialogData(context: Context) =
            DialogData(
                "견적서 작성을 취소하시겠어요?", context.getColor(R.color.text_basic_color),
                "이 화면에서 나가면 작성했던 내용이\n모두 삭제됩니다.", context.getColor(R.color.text_alert_color),
                "네", context.getColor(R.color.text_basic_color),
                "아니요", context.getColor(R.color.text_basic_color)
            )

        fun getCallToCustomerDialogData(context: Context) =
            DialogData(
                "고객과 전화 연결 하시겠어요?", context.getColor(R.color.text_basic_color),
                null, context.getColor(R.color.text_alert_color),
                "네", context.getColor(R.color.text_basic_color),
                "아니요", context.getColor(R.color.text_basic_color)
            )

        fun getQuitSignUpDialogData(context: Context) =
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

        fun getExistentUserDialogData(context: Context) =
            DialogData(
                "이미 수공의 마스터이시네요!\n 본인 인증을 통해 로그인해주세요.",
                context.getColor(R.color.text_basic_color),
                null,
                context.getColor(R.color.text_alert_color),
                "확인",
                context.getColor(R.color.text_basic_color),
                "취소",
                context.getColor(R.color.text_hint_color)
            )

        fun getUserExistDialogData(context: Context) =
            DialogData(
                "이미 존재하는 번호입니다.",
                context.getColor(R.color.text_basic_color),
                null,
                context.getColor(R.color.text_alert_color),
                "확인",
                context.getColor(R.color.text_basic_color),
                null,
                context.getColor(R.color.text_hint_color)
            )

        fun getAskingFillProfileDialogData(context: Context) =
            DialogData(
                "문의를 처리하려면\n필수 정보 등록이 필요해요.\n지금 등록하시겠어요?",
                context.getColor(R.color.text_basic_color),
                null,
                context.getColor(R.color.text_alert_color),
                "네",
                context.getColor(R.color.text_basic_color),
                "아니요",
                context.getColor(R.color.text_basic_color)
            )

        fun getWaitingUntilApprovalDialogData(context: Context) =
            DialogData(
                "승인이 완료되어야\n문의 처리가 가능해요.", context.getColor(R.color.text_basic_color),
                "승인이 완료되면 알림톡으로\n바로 알려드릴게요!", context.getColor(R.color.color_1FC472),
                "확인", context.getColor(R.color.text_basic_color),
                null, context.getColor(R.color.text_basic_color)
            )

        fun getAskingDeletePortfolioDialogData(context: Context) =
            DialogData(
                "포트폴리오를 삭제하시겠어요?", context.getColor(R.color.text_basic_color),
                null, context.getColor(R.color.text_alert_color),
                "네", context.getColor(R.color.text_basic_color),
                "아니요", context.getColor(R.color.text_basic_color)
            )

        fun getAskingDeletePriceByProjectDialogData(context: Context) =
            DialogData(
                "시공 종류별 가격을 삭제하시겠어요?", context.getColor(R.color.text_basic_color),
                null, context.getColor(R.color.text_alert_color),
                "네", context.getColor(R.color.text_basic_color),
                "아니요", context.getColor(R.color.text_basic_color)
            )

        fun getConfirmingForRequiredDialogData(context: Context) =
            DialogData(
                "해당 정보를 수정하시면,\n수공에서 승인하기 전까지 서비스 이용이 제한됩니다.",
                context.getColor(R.color.text_basic_color),
                null,
                context.getColor(R.color.text_alert_color),
                "확인",
                context.getColor(R.color.text_basic_color),
                "취소",
                context.getColor(R.color.text_basic_color)
            )

        fun getConfirmingForDeletingEstimationTemplate(context: Context) =
            DialogData(
                "제안 내용을 삭제할까요?",
                context.getColor(R.color.text_basic_color),
                "삭제된 내용은 복구가 불가능합니다.\n삭제하시겠습니까?",
                context.getColor(R.color.text_alert_color),
                "네",
                context.getColor(R.color.text_basic_color),
                "아니요",
                context.getColor(R.color.text_basic_color)
            )

        fun getConfirmingForIgnoreChangeOfEstimationTemplate(context: Context) =
            DialogData(
                "변경사항 확인",
                context.getColor(R.color.text_basic_color),
                "변경사항을 삭제하시겠습니까?",
                context.getColor(R.color.text_alert_color),
                "계속 수정",
                context.getColor(R.color.text_basic_color),
                "삭제",
                context.getColor(R.color.text_basic_color)
            )

        fun getConfirmingDirectRepairYn(context: Context) =
            DialogData(
                "직접 시공하시나요?",
                context.getColor(R.color.text_basic_color),
                "수공은 직접 시공하는 마스터님만 사용할 수\n있는 서비스입니다. 직접 시공하지 않으면\n 서비스 이용이 제한됩니다.",
                context.getColor(R.color.text_alert_color),
                "네",
                context.getColor(R.color.text_basic_color),
                "아니요",
                context.getColor(R.color.text_basic_color)
            )

        fun getNoticeForRequestMeasure(context: Context, count: Int) =
            DialogData(
                "상세상담 요청 ${count}건",
                context.getColor(R.color.text_basic_color),
                "시공을 희망하는 고객님이 기다리고\n있어요. 먼저 고객님께 연락해서\n시공확률을 높여보세요.",
                context.getColor(R.color.color_1FC472),
                "상담하러 가기",
                context.getColor(R.color.text_basic_color),
                null,
                context.getColor(R.color.text_basic_color),
            )

        fun getNoticeForRequestConsulting(context: Context, count: Int) =
            DialogData(
                "현장실측 요청 ${count}건",
                context.getColor(R.color.text_basic_color),
                "고객님이 방문 요청을 하셨어요.\n내용을 확인한 뒤\n실측여부 버튼을 클릭해 주세요.",
                context.getColor(R.color.color_1FC472),
                "확인하러 가기",
                context.getColor(R.color.text_basic_color),
                null,
                context.getColor(R.color.text_basic_color),
            )
    }
}
