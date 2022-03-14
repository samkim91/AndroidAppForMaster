package kr.co.soogong.master.data.entity.profile.portfolio

import com.google.gson.annotations.SerializedName

data class SavePriceByProjectDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("masterId")
    val masterId: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("price")
    val price: Int,
) {
    companion object
}