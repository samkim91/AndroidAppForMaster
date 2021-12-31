package kr.co.soogong.master.data.dto.requirement

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomerRequest(
    @SerializedName("requestMeasureList")
    val requestMeasureList: List<Int> = emptyList(),

    @SerializedName("requestConsultingList")
    val requestConsultingList: List<Int> = emptyList(),

    @SerializedName("isEmpty")
    val isEmpty: Boolean = false,
) : Parcelable {
    companion object
}
