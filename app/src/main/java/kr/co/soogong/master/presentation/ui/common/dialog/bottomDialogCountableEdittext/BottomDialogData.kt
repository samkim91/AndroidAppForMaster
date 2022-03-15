package kr.co.soogong.master.presentation.ui.common.dialog.bottomDialogCountableEdittext

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.R

@Parcelize
data class BottomDialogData(
    @StringRes val title: Int,
    @StringRes val hint: Int?,
    val maxCount: Int,
) : Parcelable {
    companion object {
        fun getEstimationTemplate() =
            BottomDialogData(
                title = R.string.estimation_description,
                hint = R.string.estimation_description_notice,
                maxCount= 500
            )

        fun getMemo() =
            BottomDialogData(
                title = R.string.adding_memo,
                hint = null,
                maxCount= 500
            )
    }
}
