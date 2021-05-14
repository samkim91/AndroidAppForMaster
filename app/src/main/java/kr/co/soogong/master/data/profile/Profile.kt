package kr.co.soogong.master.data.profile

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.category.BusinessType

@Parcelize
@Entity(tableName = "Profile")
data class Profile(
    @PrimaryKey
    @SerializedName("phoneNumber")
    val phoneNumber: String,    // ID

    @SerializedName("businessRepresentativeName")
    val businessRepresentativeName: String?,

    @SerializedName("businessType")
    val businessType: List<BusinessType>?,

    @SerializedName("address")
    val address: String?,

    @SerializedName("subAddress")
    val subAddress: String?,

    @SerializedName("latitude")
    val latitude: Double?,

    @SerializedName("longitude")
    val longitude: Double?,

    @SerializedName("serviceArea")
    val serviceArea: Int?,

    @SerializedName("appPush")
    val appPush: Boolean?,

    @SerializedName("appPushAtNight")
    val appPushAtNight: Boolean?,

    @SerializedName("kakaoAlarm")
    val kakaoAlarm: Boolean?,

    @SerializedName("smsAlarm")
    val smsAlarm: Boolean?,

    @SerializedName("isApproved")
    val isApproved: Boolean?,

    @SerializedName("basicInfo")
    val basicInformation: BasicInformation?,

    @SerializedName("requiredInfo")
    val requiredInformation: RequiredInformation?,
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): Profile {
            val item = jsonObject.get("data").asJsonObject
            return Profile(
                phoneNumber = item.get("phoneNumber").asString,
                businessRepresentativeName = item.get("businessRepresentativeName").asString ?: "",
                businessType = item.get("businessType").asJsonArray.map { BusinessType.fromJson(it.asJsonObject) },
                address = item.get("address").asString,
                subAddress = item.get("subAddress").asString,
                latitude = item.get("latitude").asDouble,
                longitude = item.get("longitude").asDouble,
                serviceArea = item.get("serviceArea").asInt,
                appPush = item.get("appPush").asBoolean,
                appPushAtNight = item.get("appPushAtNight").asBoolean,
                kakaoAlarm = item.get("kakaoAlarm").asBoolean,
                smsAlarm = item.get("smsAlarm").asBoolean,
                isApproved = item.get("isApproved").asBoolean,
                basicInformation = BasicInformation.fromJson(item.get("basicInfo").asJsonObject),
                requiredInformation = RequiredInformation.fromJson(item.get("requiredInfo").asJsonObject),
            )
        }
    }
}