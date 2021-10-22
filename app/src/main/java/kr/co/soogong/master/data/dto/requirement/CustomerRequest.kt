package kr.co.soogong.master.data.dto.requirement

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomerRequest(
    @SerializedName("requestMeasureList")
    val requestMeasureList: List<Int>,

    @SerializedName("requestConsultingList")
    val requestConsultingList: List<Int>,

    @SerializedName("isEmpty")
    val isEmpty: Boolean,
) : Parcelable {
    companion object {

    }
}
