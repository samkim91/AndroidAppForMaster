package kr.co.soogong.master.ui.dialog.bottomsheet

import android.content.Context
import androidx.annotation.ColorInt
import kr.co.soogong.master.R

data class BottomSheetDialogData(
    val text: String,
) {
    companion object {
        fun getWorkExperienceList() =
            List(60) { i -> BottomSheetDialogData((i + 1).toString() + "년") }

    }
}
