package kr.co.soogong.master.domain.entity.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.entity.common.AttachmentDto
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.domain.entity.common.CodeTable
import kr.co.soogong.master.domain.entity.common.major.Project
import kr.co.soogong.master.presentation.ui.profile.detail.CareerConverter
import kr.co.soogong.master.utility.PhoneNumberHelper

@Parcelize
data class RequiredInformation(
    val introduction: String?,
    val shopImages: List<AttachmentDto>?,
    val businessUnitInformation: BusinessUnitInformation?,
    val warrantyInformation: WarrantyInformation?,
    val career: String?,
    val tel: String?,
    val ownerName: String?,
    val projects: List<Project>?,
    val companyAddress: CompanyAddress?,
    val coordinate: Coordinate?,
    var serviceArea: Int?,
) : Parcelable {
    companion object {
        fun fromMasterDto(masterDto: MasterDto): RequiredInformation {
            return RequiredInformation(
                introduction = masterDto.introduction,
                shopImages = masterDto.shopImages,
                businessUnitInformation = BusinessUnitInformation(
                    businessType = masterDto.businessType?.let { CodeTable.getCodeTableByCode(it)?.inKorean },
                    businessName = masterDto.businessName,
                    shopName = masterDto.shopName,
                    businessNumber = masterDto.businessNumber,
                    businessRegistImage = masterDto.businessRegistImage,
                ),
                warrantyInformation = WarrantyInformation(
                    warrantyPeriod = masterDto.warrantyPeriod,
                    warrantyDescription = masterDto.warrantyDescription,
                ),
                career = masterDto.openDate?.let { CareerConverter.toCareer(it) },
                tel = masterDto.tel?.let { PhoneNumberHelper.addDashToLocalNumber(it) },
                ownerName = masterDto.ownerName,
                projects = Project.fromProjectDtos(masterDto.projectDtos),
                companyAddress = CompanyAddress(
                    masterDto.roadAddress,
                    masterDto.detailAddress ?: "",
                ),
                coordinate = Coordinate(
                    masterDto.latitude?.toDouble(),
                    masterDto.longitude?.toDouble(),
                ),
                serviceArea = masterDto.serviceArea,
            )
        }
    }
}