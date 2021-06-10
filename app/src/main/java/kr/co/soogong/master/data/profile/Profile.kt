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

    @SerializedName("ownerName")
    val ownerName: String?,

    @SerializedName("businessType")
    val businessType: List<BusinessType>?,

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
                ownerName = item.get("businessRepresentativeName").asString ?: "",
                businessType = item.get("businessType").asJsonArray.map { BusinessType.fromJson(it.asJsonObject) },
                appPush = item.get("appPush").asBoolean,
                appPushAtNight = item.get("appPushAtNight").asBoolean,
                kakaoAlarm = item.get("kakaoAlarm").asBoolean,
                smsAlarm = item.get("smsAlarm").asBoolean,
                isApproved = item.get("isApproved").asBoolean,
                basicInformation = BasicInformation.fromJson(item.get("basicInfo").asJsonObject),
                requiredInformation = RequiredInformation.fromJson(item.get("requiredInfo").asJsonObject),
            )
        }

        val TEST_PROFILE = Profile(
            phoneNumber = "010-7128-7964",
            ownerName = "준뱀이수공",
            businessType = listOf(BusinessType.TEST_BUSINESS_TYPE, BusinessType.TEST_BUSINESS_TYPE, BusinessType.TEST_BUSINESS_TYPE),
            appPush = false,
            appPushAtNight = false,
            kakaoAlarm = false,
            smsAlarm = false,
            isApproved = true,
            basicInformation = BasicInformation.TEST_BASIC_INFORMATION,
            requiredInformation = RequiredInformation.TEST_REQUIRED_INFORMATION,
        )

        val NULL_PROFILE = Profile(
            phoneNumber = "",
            ownerName = "",
            businessType = emptyList(),
            appPush = false,
            appPushAtNight = false,
            kakaoAlarm = false,
            smsAlarm = false,
            isApproved = true,
            basicInformation = BasicInformation.NULL_BASIC_INFORMATION,
            requiredInformation = RequiredInformation.NULL_REQUIRED_INFORMATION,
        )
    }
}