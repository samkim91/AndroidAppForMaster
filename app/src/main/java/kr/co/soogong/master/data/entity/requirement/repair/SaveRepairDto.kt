package kr.co.soogong.master.data.entity.requirement.repair

import com.google.gson.annotations.SerializedName

data class SaveRepairDto(
    @SerializedName("requirementToken")
    val requirementToken: String,

    @SerializedName("estimationId")
    val estimationId: Int,

    @SerializedName("actualDate")
    val actualDate: String,

    @SerializedName("actualPrice")
    val actualPrice: Int,

    @SerializedName("vatYn")
    val includingVat: Boolean,

    @SerializedName("warrantyDueDate")
    val warrantyDueDate: String,
)