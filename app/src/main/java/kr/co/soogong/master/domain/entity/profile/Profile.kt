package kr.co.soogong.master.domain.entity.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.contract.HttpContract
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.domain.entity.common.CodeTable

@Parcelize
data class Profile(
    val id: Int,
    val uid: String,
    val representativeName: String,
    val subscriptionPlan: String,
    val approvedStatus: CodeTable,
    val reviewSummary: ReviewSummary,
    val myPageUrl: String,
    val basicInformation: BasicInformation,
    val requiredInformation: RequiredInformation,
    val isPublic: Boolean,
    val freeMeasureYn: Boolean,
    val privatePolicy: Boolean,
    val marketingPush: Boolean,
    val pushAtNight: Boolean,
) : Parcelable {
    companion object {
        fun fromMasterDto(masterDto: MasterDto): Profile {
            return Profile(
                id = masterDto.id!!,
                uid = masterDto.uid!!,
                representativeName = if (!masterDto.shopName.isNullOrEmpty()) masterDto.shopName else masterDto.ownerName!!,
                subscriptionPlan = masterDto.subscriptionPlan!!,
                approvedStatus = CodeTable.getCodeTableByCode(masterDto.approvedStatus!!)!!,
                reviewSummary = ReviewSummary.fromMasterDto(masterDto),
                myPageUrl = HttpContract.MY_PAGE_URL + masterDto.uid,
                basicInformation = BasicInformation.fromMasterDto(masterDto),
                requiredInformation = RequiredInformation.fromMasterDto(masterDto),
                isPublic = masterDto.isPublic!!,
                freeMeasureYn = masterDto.freeMeasureYn!!,
                privatePolicy = masterDto.privatePolicy!!,
                marketingPush = masterDto.marketingPush!!,
                pushAtNight = masterDto.pushAtNight!!,
            )
        }
    }
}