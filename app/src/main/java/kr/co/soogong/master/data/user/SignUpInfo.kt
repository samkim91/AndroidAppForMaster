package kr.co.soogong.master.data.user

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.category.BusinessType

@Parcelize
data class SignUpInfo(
    val phoneNumber: String,
    val password: String,
    val businessRepresentativeName: String,
    val businessType: List<BusinessType>,
    val address: String,
    val subAddress: String,
    val latitude: Double,
    val longitude: Double,
    val serviceArea: Int,
    val acceptPrivacyPolicy: Boolean,
    val appPush: Boolean,
    val appPushAtNight: Boolean,
    val kakaoAlarm: Boolean,
    val smsAlarm: Boolean,
) : Parcelable {

}