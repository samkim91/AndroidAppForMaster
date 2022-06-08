package kr.co.soogong.master.data.entity.requirement.estimation

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RefusingMeasureDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("token")
    val token: String,

    @SerializedName("masterId")
    val masterId: Int,

    @SerializedName("masterResponseCode")
    val masterResponseCode: String,

    @SerializedName("refuseCode")
    val refuseCode: String,

    @SerializedName("refuseDescription")
    val refuseDescription: String?,
) : Parcelable {
    companion object
}