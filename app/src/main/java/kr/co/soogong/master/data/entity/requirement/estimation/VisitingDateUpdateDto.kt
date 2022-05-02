package kr.co.soogong.master.data.entity.requirement.estimation

import com.google.gson.annotations.SerializedName
import java.util.*

data class VisitingDateUpdateDto(
    @SerializedName("masterUid")
    val masterUid: String,

    @SerializedName("visitDate")
    val visitingDate: Date,
) {
    companion object
}