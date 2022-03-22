package kr.co.soogong.master.presentation.ui.common.dialog.popup

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DialogData(
    val title: String,
    val content: String,
    val alert: String,
    val positiveText: String,
    val negativeText: String,
) : Parcelable {
    companion object {
        fun getUpdatingAppMandatory() =
            DialogData(
                "업데이트 안내 (필수)",
                "앱을 사용하시려면 업데이트 해주세요.",
                "",
                "확인",
                ""
            )

        fun getUpdatingAppRecommended() =
            DialogData(
                "업데이트 안내 (선택)",
                "앱 업데이트를 통해 개선된 기능과 새로운 서비스를 이용해 보세요.",
                "",
                "확인",
                "취소"
            )

        fun getRepairInPersonIntroduction() =
            DialogData(
                "직접 시공이란?",
                "하청이 아닌 본인 또는 소속 직원이 직접 고객님들에게 서비스를 제공하는 것입니다. 하청으로 인해 발생하는 문제는 수공이 책임지지 않습니다.",
                "수공은 직접 시공하는 마스터님만을 위한 서비스입니다.",
                "확인",
                ""
            )

        fun getRefuseEstimate() =
            DialogData(
                "견적 거절",
                "견적을 거절하시면, 해당 문의가 삭제됩니다.",
                "",
                "확인",
                "취소",
            )

        fun getAlertForRequestConsult() =
            DialogData(
                "상세상담 요청",
                "고객님이 상세상담 통화를 요청하셨어요. 고객님과 빠르게 통화하고 시공 가능성을 높여보세요.",
                "24시간 동안 통화를 하지 않을 경우 배정에 불이익이 있을 수 있어요.",
                "확인",
                "",
            )

        fun getInvalidRequirement() =
            DialogData(
                "[안내]",
                "유효하지 않은 문의입니다.",
                "",
                "닫기",
                "",
            )

        fun getAcceptMeasure() =
            DialogData(
                "실측 수락",
                "수락 후 고객과 통화해 정확한 시공 내용을 파악해주세요.",
                "",
                "수락",
                "취소",
            )

        fun getRecommendingCallingCustomer() =
            DialogData(
                "전화 안내",
                "가급적 15분 내로 고객님에게 전화하여 현장방문 일정을 잡아주세요.",
                "",
                "전화하기",
                "",
            )

        fun getRefuseMeasure() =
            DialogData(
                "실측 포기",
                "실측을 포기하시겠어요?",
                "",
                "네",
                "아니요",
            )

        fun getCancelSendingEstimation() =
            DialogData(
                "작성 취소",
                "이 화면에서 나가면 작성했던 내용이 모두 삭제됩니다.",
                "",
                "확인",
                "취소",
            )

        fun getCallToCustomer(phoneNumber: String) =
            DialogData(
                "고객님에게 전화하시겠어요?",
                phoneNumber,
                "",
                "확인",
                "취소",
            )

        fun getConfirmingQuitSignUp() =
            DialogData(
                "잠깐!",
                "가입신청을 그만두지 마세요. 지금 가입하면 문의 수신, 견적 발송이 무료!",
                "이 화면에서 나가면 작성했던 내용이 모두 삭제됩니다.",
                "가입 할래요",
                "그만 할래요",
            )

        fun getExistentUser() =
            DialogData(
                "이미 수공의 마스터이시네요!",
                "본인 인증을 통해 로그인해주세요.",
                "",
                "확인",
                "",
            )

        fun getUserExist() =
            DialogData(
                "잠깐!",
                "이미 존재하는 번호입니다.",
                "",
                "확인",
                "",
            )

        fun notifyRequiredProfileInformation() =
            DialogData(
                "[주의]",
                "수공 마스터앱을 이용하시려면 추가적인 마스터님의 정보가 필요해요!",
                "",
                "확인",
                "",
            )

        fun getAskingFillRequiredProfile() =
            DialogData(
                "잠시만요!",
                "마스터 승인에 필요한 필수 정보를 아직 채우지 않았어요!",
                "",
                "프로필 설정하기",
                "나중에 하기",
            )

        fun getAskingFillBasicProfile() =
            DialogData(
                "잠시만요!",
                "선택정보를 넣으면 고객과의 매칭률이 올라갑니다!",
                "",
                "선택정보 채우기",
                "나중에 하기",
            )

        fun getWaitingUntilApproval() =
            DialogData(
                "[주의]",
                "승인이 완료되어야 문의 처리가 가능해요. 승인이 완료되면 알림톡으로 바로 알려드릴게요!",
                "",
                "확인",
                "",
            )

        fun getAskingDeletePortfolio() =
            DialogData(
                "[주의]",
                "포트폴리오를 삭제하시겠어요?",
                "",
                "네",
                "아니요",
            )

        fun getAskingDeleteRepairPhoto() =
            DialogData(
                "[주의]",
                "시공 사진을 삭제하시겠어요?",
                "",
                "네",
                "아니요",
            )

        fun getAskingDeletePriceByProject() =
            DialogData(
                "[주의]",
                "시공 종류별 가격을 삭제하시겠어요?",
                "",
                "네",
                "아니요",
            )

        fun getConfirmingForLimitedService() =
            DialogData(
                "[주의]",
                "해당 정보를 수정하시면, 수공에서 승인하기 전까지 서비스 이용이 제한됩니다.",
                "",
                "확인",
                "취소",
            )

        fun getConfirmingForDeletingEstimationTemplate() =
            DialogData(
                "[주의]",
                "삭제된 내용은 복구가 불가능합니다. 삭제하시겠습니까?",
                "",
                "네",
                "아니요",
            )

        fun getConfirmingForIgnoreChange() =
            DialogData(
                "[주의]",
                "내용을 저장하지 않으셨습니다. 저장하지 않은 내용은 복구가 불가능합니다.",
                "",
                "계속 수정",
                "닫기",
            )

        fun getNoticeForRequestMeasure(count: Int) =
            DialogData(
                "현장실측 요청 ${count}건",
                "고객님이 방문 요청을 하셨어요. 내용을 확인한 뒤 실측여부 버튼을 클릭해 주세요.",
                "",
                "확인하러 가기",
                "닫기",
            )

        fun getNoticeForRequestConsulting(count: Int) =
            DialogData(
                "상세상담 요청 ${count}건",
                "시공을 희망하는 고객님이 기다리고 있어요. 먼저 고객님께 연락해서 시공확률을 높여보세요.",
                "",
                "상담하러 가기",
                "닫기",
            )

        fun getConfirmingLogout() =
            DialogData(
                "로그아웃",
                "정말 로그아웃 하시겠습니까?",
                "",
                "네",
                "아니요",
            )
    }
}
