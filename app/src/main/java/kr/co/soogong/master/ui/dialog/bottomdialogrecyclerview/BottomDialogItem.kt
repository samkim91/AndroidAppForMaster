package kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BottomDialogItem(
    val key: String,
    val value: Int,
    val type: Int = SIMPLE_BOTTOM_DIALOG,
) : Parcelable {
    companion object {
        const val SIMPLE_BOTTOM_DIALOG = 100
        const val ICON_BOTTOM_DIALOG = 200

        fun getServiceAreaList(): List<BottomDialogItem> =
            listOf(
                BottomDialogItem("2km", 2),
                BottomDialogItem("5km", 5),
                BottomDialogItem("10km", 10),
                BottomDialogItem("25km", 25),
                BottomDialogItem("50km", 50),
                BottomDialogItem("100km", 100),
                BottomDialogItem("전국구 가능", 1000),
            )

        fun getCareerYearList(): List<BottomDialogItem> =
            listOf(
                BottomDialogItem("1년", 1),
                BottomDialogItem("2년", 2),
                BottomDialogItem("3년", 3),
                BottomDialogItem("4년", 4),
                BottomDialogItem("5년", 5),
                BottomDialogItem("6년", 6),
                BottomDialogItem("7년", 7),
                BottomDialogItem("8년", 8),
                BottomDialogItem("9년", 9),
                BottomDialogItem("10년", 10),
                BottomDialogItem("11년", 11),
                BottomDialogItem("12년", 12),
                BottomDialogItem("13년", 13),
                BottomDialogItem("14년", 14),
                BottomDialogItem("15년", 15),
                BottomDialogItem("16년", 16),
                BottomDialogItem("17년", 17),
                BottomDialogItem("18년", 18),
                BottomDialogItem("19년", 19),
                BottomDialogItem("20년", 20),
                BottomDialogItem("21년", 21),
                BottomDialogItem("22년", 22),
                BottomDialogItem("23년", 23),
                BottomDialogItem("24년", 24),
                BottomDialogItem("25년", 25),
                BottomDialogItem("26년", 26),
                BottomDialogItem("27년", 27),
                BottomDialogItem("28년", 28),
                BottomDialogItem("29년", 29),
                BottomDialogItem("30년", 30),
                BottomDialogItem("31년", 31),
                BottomDialogItem("32년", 32),
                BottomDialogItem("33년", 33),
                BottomDialogItem("34년", 34),
                BottomDialogItem("35년", 35),
                BottomDialogItem("36년", 36),
                BottomDialogItem("37년", 37),
                BottomDialogItem("38년", 38),
                BottomDialogItem("39년", 39),
                BottomDialogItem("40년", 40),
            )

        fun getWarrantyPeriodList(): List<BottomDialogItem> =
            listOf(
                BottomDialogItem("보증기간 없음", -1),
                BottomDialogItem("직접 입력", 0),
                BottomDialogItem("1년", 1),
                BottomDialogItem("2년", 2),
                BottomDialogItem("3년", 3),
                BottomDialogItem("4년", 4),
                BottomDialogItem("5년", 5),
                BottomDialogItem("6년", 6),
                BottomDialogItem("7년", 7),
                BottomDialogItem("8년", 8),
                BottomDialogItem("9년", 9),
                BottomDialogItem("10년", 10),
            )

        fun getEmailDomainsList(): List<BottomDialogItem> =
            listOf(
                BottomDialogItem("naver.com", 0),
                BottomDialogItem("gmail.com", 0),
                BottomDialogItem("kakao.com", 0),
                BottomDialogItem("daum.com", 0),
                BottomDialogItem("hanmail.com", 0),
                BottomDialogItem("hotmail.com", 0),
                BottomDialogItem("yahoo.com", 0),
                BottomDialogItem("nate.com", 0),
                BottomDialogItem("이메일 직접입력", 0),
            )

        fun getRequestingReviewList(): List<BottomDialogItem> =
            listOf(
                BottomDialogItem("메시지로 공유하기", 0, ICON_BOTTOM_DIALOG),
                BottomDialogItem("카카오톡으로 공유하기", 0, ICON_BOTTOM_DIALOG),
                BottomDialogItem("다른 방법으로 공유하기", 0, ICON_BOTTOM_DIALOG),
                BottomDialogItem("링크만 복사", 0, ICON_BOTTOM_DIALOG),
            )
    }
}
