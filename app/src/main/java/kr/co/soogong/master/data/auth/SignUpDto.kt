package kr.co.soogong.master.data.auth

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.category.BusinessType

@Parcelize
data class SignUpDto(
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

    @SerializedName("privacyPolicy")
    val privacyPolicy: Boolean,

    @SerializedName("appPush")
    val appPush: Boolean,

    @SerializedName("marketingPush")
    val marketingPush: Boolean,
) : Parcelable { }