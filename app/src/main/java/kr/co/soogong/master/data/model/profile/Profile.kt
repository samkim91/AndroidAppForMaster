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

    @SerializedName("requiredInformation")
    val requiredInformation: RequiredInformation?,
) : Parcelable {
    companion object {
        fun fromMasterDto(masterDto: MasterDto): Profile {
            return Profile(
                id = masterDto.id!!,
                uid = masterDto.uid,
                tel = masterDto.tel,
                representativeName = if (!masterDto.shopName.isNullOrEmpty()) masterDto.shopName else masterDto.ownerName,
                subscriptionPlan = masterDto.subscriptionPlan,
                myReview = null, // TODO: 2021/06/15 추가 필요 masterDto.myReview,
                myPageUrl = HttpContract.MY_PAGE_URL + masterDto.uid,
                isPublic = masterDto.isPublic,
                basicInformation = BasicInformation.fromMasterDto(masterDto),
                requiredInformation = RequiredInformation.fromMasterDto(masterDto)
            )
        }
    }
}