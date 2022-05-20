package kr.co.soogong.master.data.entity.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MasterSettingsDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("uid")
    val uid: String,

    @SerializedName("approvedStatus")
    val approvedStatus: String,

    @SerializedName("requestMeasureYn")
    val requestMeasureYn: Boolean,
) : Parcelable {
    companion object
}