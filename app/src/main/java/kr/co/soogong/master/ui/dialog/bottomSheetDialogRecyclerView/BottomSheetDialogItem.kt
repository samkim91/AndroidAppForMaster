package kr.co.soogong.master.ui.dialog.bottomSheetDialogRecyclerView

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.R

@Parcelize
data class BottomSheetDialogItem(
    val key: String,
    val value: Int,
    val type: Int = DEFAULT,
    @DrawableRes val icon: Int? = null,
) : Parcelable {
    companion object {
        const val DEFAULT = 100
        const val ICON = 200

        fun getServiceAreaList(): List<BottomSheetDialogItem> =
            listOf(
                BottomSheetDialogItem("2km", 2),
                BottomSheetDialogItem("5km", 5),
                BottomSheetDialogItem("10km", 10),
                BottomSheetDialogItem("25km", 25),
                BottomSheetDialogItem("50km", 50),
                BottomSheetDialogItem("100km", 100),
                BottomSheetDialogItem("전국구 가능", 1000),
            )

        fun getCareerYearList(): List<BottomSheetDialogItem> =
            listOf(
                BottomSheetDialogItem("1년", 1),
                BottomSheetDialogItem("2년", 2),
                BottomSheetDialogItem("3년", 3),
                BottomSheetDialogItem("4년", 4),
                BottomSheetDialogItem("5년", 5),
                BottomSheetDialogItem("6년", 6),
                BottomSheetDialogItem("7년", 7),
                BottomSheetDialogItem("8년", 8),
                BottomSheetDialogItem("9년", 9),
                BottomSheetDialogItem("10년", 10),
                BottomSheetDialogItem("11년", 11),
                BottomSheetDialogItem("12년", 12),
                BottomSheetDialogItem("13년", 13),
                BottomSheetDialogItem("14년", 14),
                BottomSheetDialogItem("15년", 15),
                BottomSheetDialogItem("16년", 16),
                BottomSheetDialogItem("17년", 17),
                BottomSheetDialogItem("18년", 18),
                BottomSheetDialogItem("19년", 19),
                BottomSheetDialogItem("20년", 20),
                BottomSheetDialogItem("21년", 21),
                BottomSheetDialogItem("22년", 22),
                BottomSheetDialogItem("23년", 23),
                BottomSheetDialogItem("24년", 24),
                BottomSheetDialogItem("25년", 25),
                BottomSheetDialogItem("26년", 26),
                BottomSheetDialogItem("27년", 27),
                BottomSheetDialogItem("28년", 28),
                BottomSheetDialogItem("29년", 29),
                BottomSheetDialogItem("30년", 30),
                BottomSheetDialogItem("31년", 31),
                BottomSheetDialogItem("32년", 32),
                BottomSheetDialogItem("33년", 33),
                BottomSheetDialogItem("34년", 34),
                BottomSheetDialogItem("35년", 35),
                BottomSheetDialogItem("36년", 36),
                BottomSheetDialogItem("37년", 37),
                BottomSheetDialogItem("38년", 38),
                BottomSheetDialogItem("39년", 39),
                BottomSheetDialogItem("40년", 40),
            )

        fun getRequestingReviewList(): List<BottomSheetDialogItem> =
            listOf(
                BottomSheetDialogItem("메시지로 공유하기", 0, ICON, R.drawable.ic_message),
                BottomSheetDialogItem("카카오톡으로 공유하기", 1, ICON, R.drawable.ic_kakaotalk),
                BottomSheetDialogItem("다른 방법으로 공유하기", 2, ICON, R.drawable.ic_three_dots),
                BottomSheetDialogItem("링크만 복사", 3, ICON, R.drawable.ic_copy_link),
            )
    }
}
