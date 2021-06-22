package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.dto.profile.MasterDto

@Parcelize
data class Profile(
    @SerializedName("id")
    val id: Int,

    @SerializedName("uid")
    val uid: String?,

    @SerializedName("tel")
    val tel: String?,

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
                id = masterDto.id!!,
                uid = masterDto.uid,
                tel = masterDto.tel,
                representativeName = if (!masterDto.shopName.isNullOrEmpty()) masterDto.shopName else masterDto.ownerName,
                subscriptionPlan = masterDto.subscriptionPlan,
                myReview = MyReview.TEST_MY_REVIEW, // TODO: 2021/06/15 추가 필요 masterDto.myReview,
                myPageUrl = HttpContract.MY_PAGE_URL + masterDto.uid,
                isPublic = masterDto.isPublic,
                basicInformation = BasicInformation.fromMasterDto(masterDto),
            )
        }

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