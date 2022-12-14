package kr.co.soogong.master.data.entity.auth

import com.google.gson.annotations.SerializedName

data class MasterSignUpDto(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("uid")
    val uid: String? = null,

    @SerializedName("ownerName")
    val ownerName: String,

    @SerializedName("tel")
    val tel: String,

    @SerializedName("privatePolicy")
    val privatePolicy: Boolean,

    @SerializedName("marketingPush")
    val marketingPush: Boolean,

    @SerializedName("pushAtNight")
    val pushAtNight: Boolean,

    @SerializedName("directRepairYn")
    val repairInPerson: Boolean,
)