package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.dto.profile.MasterDto

@Parcelize
@Entity(tableName = "Profile")
data class Profile(
    @SerializedName("id")
    val id: Int,

    @PrimaryKey
    @SerializedName("uId")
    val uId: String,

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
        fun fromMasterDto(masterDto: MasterDto): Profile {
            return Profile(
                id = masterDto.id,
                uId = masterDto.uId,
                tel = masterDto.tel,
                representativeName = if (!masterDto.shopName.isNullOrEmpty()) masterDto.shopName else masterDto.ownerName,
                subscriptionPlan = masterDto.subscriptionPlan,
                myReview = MyReview.TEST_MY_REVIEW, // TODO: 2021/06/15 추가 필요 masterDto.myReview,
                myPageUrl = HttpContract.MY_PAGE_URL + masterDto.uId,
                isPublic = masterDto.isPublic,
                basicInformation = BasicInformation.fromMasterDto(masterDto),
            )
        }

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
            uId = "",
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