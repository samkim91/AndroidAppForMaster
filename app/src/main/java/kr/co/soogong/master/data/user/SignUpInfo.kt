package kr.co.soogong.master.data.user

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.category.BusinessType

@Parcelize
data class SignUpInfo(
    @SerializedName("phoneNumber")
    val phoneNumber: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("ownerName")
    val businessRepresentativeName: String,

    @SerializedName("businessType")
    val businessType: List<BusinessType>,

    @SerializedName("address")
    val address: String,

    @SerializedName("subAddress")
    val subAddress: String,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("serviceArea")
    val serviceArea: Int,

    @SerializedName("acceptPrivacyPolicy")
    val acceptPrivacyPolicy: Boolean,

    @SerializedName("appPush")
    val appPush: Boolean,

    @SerializedName("appPushAtNight")
    val appPushAtNight: Boolean,

    @SerializedName("kakaoAlarm")
    val kakaoAlarm: Boolean,

    @SerializedName("smsAlarm")
    val smsAlarm: Boolean,
) : Parcelable { }