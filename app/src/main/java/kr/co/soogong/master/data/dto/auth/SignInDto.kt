package kr.co.soogong.master.data.dto.auth

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.model.major.BusinessType

@Parcelize
data class SignInDto(
    @SerializedName("uid")
    val uid: String,

    @SerializedName("ownerName")
    val ownerName: String,

    @SerializedName("tel")
    val tel: String,

    @SerializedName("roadAddress")
    val roadAddress: String,

    @SerializedName("detailAddress")
    val detailAddress: String,

    @SerializedName("businessType")
    val businessType: List<BusinessType>,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("serviceArea")
    val serviceArea: Int,

    @SerializedName("privacyPolicy")
    val privacyPolicy: Boolean,

    @SerializedName("marketingPush")
    val marketingPush: Boolean,

    @SerializedName("marketingPushAtNight")
    val marketingPushAtNight: Boolean,

    @SerializedName("appPush")
    val appPush: Boolean,

    @SerializedName("kakaoPush")
    val kakaoPush: Boolean,

    @SerializedName("smsPush")
    val smsPush: Boolean,

) : Parcelable { }