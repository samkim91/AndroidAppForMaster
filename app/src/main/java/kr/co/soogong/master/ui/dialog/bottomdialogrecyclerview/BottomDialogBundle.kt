package kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BottomDialogBundle(
    val title: String,
    val subtitle: String,
    val list: List<BottomDialogItem>,
) : Parcelable {
    companion object {
        fun getServiceAreaBundle(): BottomDialogBundle =
            BottomDialogBundle(
                title = "",
                subtitle = "범위 선택",
                list = BottomDialogItem.getServiceAreaList(),
            )

        fun getIncreasingYearBundle(type: String): BottomDialogBundle =
            BottomDialogBundle(
                title = "",
                subtitle = when (type) {
                    "career" -> "경력"
                    "warranty" -> "A/S 보증기간"
                    else -> ""
                },
                list = BottomDialogItem.getIncreasingYearList(),
            )

        fun getEmailDomainsBundle() =
            BottomDialogBundle(
                title = "",
                subtitle = "이메일 주소",
                list = BottomDialogItem.getEmailDomainsList(),
            )

        fun getRequestingReviewBundle() =
            BottomDialogBundle(
                title = "직접 시공했던 고객에게\n" +
                        "링크를 공유해 리뷰를 쌓아보세요!",
                subtitle = "리뷰가 늘어날 수록 시공 확률이 높아집니다.",
                list = BottomDialogItem.getRequestingReviewList(),
            )
    }
}

