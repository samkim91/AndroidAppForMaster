package kr.co.soogong.master.ui.dialog.bottomdialogbutton

import android.content.Context
import androidx.annotation.ColorInt
import kr.co.soogong.master.R

data class BottomDialogButtonData(
    var title: String?,
    @ColorInt var titleTxtColor: Int,
    var leftButtonText: String?,
    @ColorInt var leftButtonTextColor: Int,
    var rightButtonText: String?,
    @ColorInt var rightButtonTextColor: Int
) {
    companion object {
        fun getDialogDataForImage(context: Context) =
            BottomDialogButtonData(
                "이미지를 가져와주세요.", context.getColor(R.color.color_616161),
                "카메라", context.getColor(R.color.text_basic_color),
                "갤러리", context.getColor(R.color.text_basic_color)
            )
    }
}
