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

        fun getIncreasingYearList(): List<BottomDialogItem> =
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
