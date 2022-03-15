package kr.co.soogong.master.data.entity.requirement.estimation

import com.google.gson.annotations.SerializedName

data class SaveMasterMemoDto(
    @SerializedName("masterUid")
    val masterUid: String,

    @SerializedName("masterMemo")
    val masterMemo: String,
) {
    companion object
}