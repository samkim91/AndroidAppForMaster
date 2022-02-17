package kr.co.soogong.master.domain.entity.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.entity.common.AttachmentDto
import kr.co.soogong.master.data.entity.profile.MasterConfigDto
import kr.co.soogong.master.data.entity.profile.MasterDto

@Parcelize
data class BasicInformation(
    val freeMeasureYn: Boolean,
    val portfolioCount: Int,
    val priceByProjectCount: Int,
    val profileImage: AttachmentDto?,
    val masterConfigs: List<MasterConfigDto>?,
    val email: String?,
) : Parcelable {
    companion object {
        fun fromMasterDto(masterDto: MasterDto): BasicInformation {
            return BasicInformation(
                freeMeasureYn = masterDto.freeMeasureYn!!,
                portfolioCount = masterDto.portfolioCount ?: 0,
                priceByProjectCount = masterDto.priceByProjectCount ?: 0,
                profileImage = masterDto.profileImage,
                masterConfigs = masterDto.masterConfigDtos,
                email = masterDto.email,
            )
        }
    }
}