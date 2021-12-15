package kr.co.soogong.master.ui.dialog.bottomSheetDialogRecyclerView

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BottomSheetDialogBundle(
    val title: String,
    val subtitle: String,
    val list: List<BottomSheetDialogItem>,
) : Parcelable {
    companion object {
        fun getServiceAreaBundle(): BottomSheetDialogBundle =
            BottomSheetDialogBundle(
                title = "범위 선택",
                subtitle = "",
                list = BottomSheetDialogItem.getServiceAreaList(),
            )

        fun getCareerYearBundle(): BottomSheetDialogBundle =
            BottomSheetDialogBundle(
                title = "경력",
                subtitle = "",
                list = BottomSheetDialogItem.getCareerYearList(),
            )

        fun getEmailDomainsBundle() =
            BottomSheetDialogBundle(
                title = "이메일 주소",
                subtitle = "",
                list = BottomSheetDialogItem.getEmailDomainsList(),
            )

        fun getRequestingReviewBundle() =
            BottomSheetDialogBundle(
                title = "직접 시공했던 고객에게 링크를 공유해 리뷰를 쌓아보세요!",
                subtitle = "리뷰가 늘어날 수록 시공 확률이 높아집니다.",
                list = BottomSheetDialogItem.getRequestingReviewList(),
            )
    }
}

