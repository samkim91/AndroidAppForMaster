package kr.co.soogong.master.data.dto.home

import com.google.gson.annotations.SerializedName

data class RequirementTotalDto(
    @SerializedName("beforeProcessCount")
    val beforeProcessCount: Int,

    @SerializedName("processingCount")
    val processingCount: Int,

    @SerializedName("afterProcessCount")
    val afterProcessCount: Int,
) {
    companion object
}