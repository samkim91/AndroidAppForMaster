package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.major.Major
import kr.co.soogong.master.ui.profile.detail.requiredinformation.CareerConverter

@Parcelize
data class RequiredInformation(
    val introduction: String?,
    val shopImages: List<AttachmentDto>?,
    val businessUnitInformation: BusinessUnitInformation?,
    val warrantyInformation: WarrantyInformation?,
    val career: String?,
    val tel: String?,
    val ownerName: String?,
    val majors: List<Major>?,
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
                    businessType = masterDto.businessType?.let {CodeTable.findBusinessTypeByCode(it).inKorean},
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
                tel = masterDto.tel,
                ownerName = masterDto.ownerName,
                majors = Major.fromMasterDto(masterDto),
                companyAddress = CompanyAddress(
                    masterDto.roadAddress,
                    masterDto.detailAddress,
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