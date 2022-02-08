package kr.co.soogong.master.domain.entity.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.entity.profile.MasterDto

@Parcelize
data class Profile(
    val id: Int,
    val uid: String?,
    val representativeName: String?,
    val subscriptionPlan: String?,
    val approvedStatus: String?,
    val reviewSummary: ReviewSummary?,
    val myPageUrl: String?,
    val isPublic: Boolean?,
    val basicInformation: BasicInformation?,
    val requiredInformation: RequiredInformation?,
) : Parcelable {
    companion object {
        fun fromMasterDto(masterDto: MasterDto): Profile {
            return Profile(
                id = masterDto.id!!,
                uid = masterDto.uid,
                representativeName = if (!masterDto.shopName.isNullOrEmpty()) masterDto.shopName else masterDto.ownerName,
                subscriptionPlan = masterDto.subscriptionPlan,
                approvedStatus = masterDto.approvedStatus,
                reviewSummary = ReviewSummary.fromMasterDto(masterDto),
                myPageUrl = HttpContract.MY_PAGE_URL + masterDto.uid,
                isPublic = masterDto.isPublic,
                basicInformation = BasicInformation.fromMasterDto(masterDto),
                requiredInformation = RequiredInformation.fromMasterDto(masterDto)
            )
        }
    }
}