package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Profile")
data class Profile(
    @SerializedName("id")
    val id: Int,

    @PrimaryKey
    @SerializedName("uid")
    val uid: String,

    @SerializedName("tel")
    val tel: String,

    @SerializedName("representativeName")
    val representativeName: String?,

    @SerializedName("subscriptionPlan")
    val subscriptionPlan: String?,

    @SerializedName("myReview")
    val myReview: MyReview?,

    @SerializedName("myPageUrl")
    val myPageUrl: String?,

    @SerializedName("isPublic")
    val isPublic: Boolean?,

    @SerializedName("basicInformation")
    val basicInformation: BasicInformation?,
) : Parcelable {
    companion object {

        //        fun fromJson(jsonObject: JsonObject): Profile {
//            val item = jsonObject.get("data").asJsonObject
//            return Profile(
//                phoneNumber = item.get("phoneNumber").asString,
//                ownerName = item.get("businessRepresentativeName").asString ?: "",
//                businessType = item.get("businessType").asJsonArray.map { BusinessType.fromJson(it.asJsonObject) },
//                appPush = item.get("appPush").asBoolean,
//                appPushAtNight = item.get("appPushAtNight").asBoolean,
//                kakaoAlarm = item.get("kakaoAlarm").asBoolean,
//                smsAlarm = item.get("smsAlarm").asBoolean,
//                isApproved = item.get("isApproved").asBoolean,
//                basicInformation = BasicInformation.fromJson(item.get("basicInfo").asJsonObject),
//                requiredInformation = RequiredInformation.fromJson(item.get("requiredInfo").asJsonObject),
//            )
//        }
//
//        val TEST_PROFILE = Profile(
//            phoneNumber = "010-7128-7964",
//            ownerName = "준뱀이수공",
//            businessType = listOf(BusinessType.TEST_BUSINESS_TYPE, BusinessType.TEST_BUSINESS_TYPE, BusinessType.TEST_BUSINESS_TYPE),
//            appPush = false,
//            appPushAtNight = false,
//            kakaoAlarm = false,
//            smsAlarm = false,
//            isApproved = true,
//            basicInformation = BasicInformation.TEST_BASIC_INFORMATION,
//            requiredInformation = RequiredInformation.TEST_REQUIRED_INFORMATION,
//        )
//
        val NULL_PROFILE = Profile(
            id = 0,
            uid = "",
            tel = "",
            representativeName = "",
            subscriptionPlan = "",
            myReview = MyReview.NULL_MY_REVIEW,
            myPageUrl = "",
            isPublic = false,
            basicInformation = BasicInformation.NULL_BASIC_INFORMATION,
        )
    }
}