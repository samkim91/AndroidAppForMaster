package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.major.Major
import kr.co.soogong.master.data.model.requirement.ImagePath

@Parcelize
data class RequiredInformation(
    val introduction: String?,
    val representativeImages: List<ImagePath>?,
    val businessUnitInformation: BusinessUnitInformation?,
    val warrantyInformation: WarrantyInformation?,
    val career: String?,
    val tel: String,
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
                    businessRegistImage = ImagePath("masterDto.businessRegistImageId"), // TODO: 2021/06/15 get data from server
                ),
                warrantyInformation = WarrantyInformation(
                    warrantyPeriod = "${masterDto.warrantyPeriod}년",
                    warrantyDescription = masterDto.warrantyDescription,
                ),
                career = masterDto.introduction,
                tel = masterDto.tel,
                ownerName = masterDto.ownerName,
                majors = masterDto.projects,
                companyAddress = CompanyAddress(
                    masterDto.roadAddress,
                    masterDto.detailAddress,
                ),
                coordinate = Coordinate(
                    masterDto.latitude?.toDouble(),
                    masterDto.longitude?.toDouble()
                ),
                serviceArea = masterDto.serviceArea,
            )
        }

        fun fromJson(jsonObject: JsonObject): RequiredInformation {
            val item = jsonObject.get("data").asJsonObject
            val attributes = item.get("attributes").asJsonObject

            return RequiredInformation(
                introduction = attributes.get("briefIntroduction").asString,
                representativeImages = attributes.get("representativeImages").asJsonArray.map {
                    ImagePath.fromJson(
                        it.asJsonObject
                    )
                },
                businessUnitInformation = BusinessUnitInformation.fromJson(attributes.get("businessUnitInformation").asJsonObject),
                warrantyInformation = WarrantyInformation.fromJson(attributes.get("warrantyInformation").asJsonObject),
                career = attributes.get("career").asString,
                tel = attributes.get("phoneNumber").asString,
                ownerName = attributes.get("businessRepresentativeName").asString,
                majors = attributes.get("businessTypes").asJsonArray.map {
                    Major.fromJson(
                        it.asJsonObject
                    )
                },
                companyAddress = CompanyAddress.fromJson(attributes.get("companyAddress").asJsonObject),
                coordinate = Coordinate.fromJson(attributes.get("coordinate").asJsonObject),
                serviceArea = attributes.get("serviceArea").asInt,
            )
        }


        val TEST_REQUIRED_INFORMATION = RequiredInformation(
            introduction = "강남지역 30년 역사의 싱크대 장인! 믿고 맡게 주세요! 강남지역 30년 역사의 싱크대 장인! 믿고 맡게 주세요! 강남지역 30년 역사의 싱크대 장인! 믿고 맡게 주세요!",
            representativeImages = listOf(
                ImagePath.TEST_IMAGE_PATH,
                ImagePath.TEST_IMAGE_PATH,
                ImagePath.TEST_IMAGE_PATH
            ),
            businessUnitInformation = BusinessUnitInformation.TEST_BUSINESS_UNIT_INFORMATION_2,
            warrantyInformation = WarrantyInformation.TEST_WARRANTY_INFORMATION,
            career = "2년",
            tel = "010 - 3290 -1234",
            ownerName = "성현식",
            majors = listOf(Major.TEST_BUSINESS_TYPE, Major.TEST_BUSINESS_TYPE),
            companyAddress = CompanyAddress.TEST_COMPANY_ADDRESS,
            coordinate = Coordinate.TEST_COORDINATE,
            serviceArea = 10
        )

        val NULL_REQUIRED_INFORMATION = RequiredInformation(
            introduction = "",
            representativeImages = emptyList(),
            ownerName = "",
            businessUnitInformation = BusinessUnitInformation.NULL_BUSINESS_UNIT_INFORMATION,
            warrantyInformation = WarrantyInformation.NULL_WARRANTY_INFORMATION,
            career = "",
            tel = "",
            majors = emptyList(),
            companyAddress = CompanyAddress.NULL_COMPANY_ADDRESS,
            coordinate = Coordinate.NULL_COORDINATE,
            serviceArea = 0
        )
    }
}