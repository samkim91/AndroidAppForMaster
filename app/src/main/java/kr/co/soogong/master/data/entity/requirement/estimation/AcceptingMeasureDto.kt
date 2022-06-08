package kr.co.soogong.master.data.entity.requirement.estimation

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AcceptingMeasureDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("token")
    val token: String,

    @SerializedName("masterId")
    val masterId: Int,

    @SerializedName("masterResponseCode")
    val masterResponseCode: String,
) : Parcelable {
    companion object
}