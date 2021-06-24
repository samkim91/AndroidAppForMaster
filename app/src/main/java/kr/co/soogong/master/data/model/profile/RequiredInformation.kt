package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.major.Major

@Parcelize
data class RequiredInformation(
    val introduction: String?,
    val representativeImages: List<AttachmentDto>?,
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
                representativeImages = emptyList(),  // TODO: 2021/06/15 get data from server
                businessUnitInformation = BusinessUnitInformation(
                    businessType = masterDto.businessType,
                    businessName = masterDto.businessName,
                    shopName = masterDto.shopName,
                    businessNumber = masterDto.businessNumber,
                    businessRegistImage = null, // TODO: 2021/06/15 get data from server
                ),
                warrantyInformation = WarrantyInformation(
                    warrantyPeriod = "${masterDto.warrantyPeriod}년",
                    warrantyDescription = masterDto.warrantyDescription,
                ),
                career = masterDto.introduction,
                tel = masterDto.tel,
                ownerName = masterDto.ownerName,
                majors = emptyList(),  // TODO: 2021/06/16 projectDto가 변화되는 것을 보고 바꿔줘야함.
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