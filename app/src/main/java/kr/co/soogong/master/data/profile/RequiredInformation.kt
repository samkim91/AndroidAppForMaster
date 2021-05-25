package kr.co.soogong.master.data.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.category.BusinessType
import kr.co.soogong.master.data.estimation.ImagePath
import kr.co.soogong.master.data.user.Coordinate

@Parcelize
data class RequiredInformation(
    val briefIntroduction: String,
    val representativeImages: List<ImagePath>,
    val businessUnitInformation: BusinessUnitInformation,
    val warrantyInformation: WarrantyInformation,
    val career: String,
    val phoneNumber: String,
    val businessRepresentativeName: String,
    val businessTypes: List<BusinessType>,
    val companyAddress: CompanyAddress,
    val coordinate: Coordinate,
    var serviceArea: Int,
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): RequiredInformation {
            val item = jsonObject.get("data").asJsonObject
            val attributes = item.get("attributes").asJsonObject

            return RequiredInformation(
                briefIntroduction = attributes.get("briefIntroduction").asString,
                representativeImages = attributes.get("representativeImages").asJsonArray.map {
                    ImagePath.fromJson(
                        it.asJsonObject
                    )
                },
                businessUnitInformation = BusinessUnitInformation.fromJson(attributes.get("businessUnitInformation").asJsonObject),
                warrantyInformation = WarrantyInformation.fromJson(attributes.get("warrantyInformation").asJsonObject),
                career = attributes.get("career").asString,
                phoneNumber = attributes.get("phoneNumber").asString,
                businessRepresentativeName = attributes.get("businessRepresentativeName").asString,
                businessTypes = attributes.get("businessTypes").asJsonArray.map {
                    BusinessType.fromJson(
                        it.asJsonObject
                    )
                },
                companyAddress = CompanyAddress.fromJson(attributes.get("companyAddress").asJsonObject),
                coordinate = Coordinate.fromJson(attributes.get("coordinate").asJsonObject),
                serviceArea = attributes.get("serviceArea").asInt,
            )
        }

        val TEST_REQUIRED_INFORMATION = RequiredInformation(
            briefIntroduction = "강남지역 30년 역사의 싱크대 장인! 믿고 맡게 주세요! 강남지역 30년 역사의 싱크대 장인! 믿고 맡게 주세요! 강남지역 30년 역사의 싱크대 장인! 믿고 맡게 주세요!",
            representativeImages = listOf(
                ImagePath.TEST_IMAGE_PATH,
                ImagePath.TEST_IMAGE_PATH,
                ImagePath.TEST_IMAGE_PATH
            ),
            businessUnitInformation = BusinessUnitInformation.TEST_BUSINESS_UNIT_INFORMATION_2,
            warrantyInformation = WarrantyInformation.TEST_WARRANTY_INFORMATION,
            career = "2년",
            phoneNumber = "010 - 3290 -1234",
            businessRepresentativeName = "성현식",
            businessTypes = listOf(BusinessType.TEST_BUSINESS_TYPE, BusinessType.TEST_BUSINESS_TYPE),
            companyAddress = CompanyAddress.TEST_COMPANY_ADDRESS,
            coordinate = Coordinate.TEST_COORDINATE,
            serviceArea = 10
        )

        val NULL_REQUIRED_INFORMATION = RequiredInformation(
            briefIntroduction = "",
            representativeImages = emptyList(),
            businessRepresentativeName = "",
            businessUnitInformation = BusinessUnitInformation.NULL_BUSINESS_UNIT_INFORMATION,
            warrantyInformation = WarrantyInformation.NULL_WARRANTY_INFORMATION,
            career = "",
            phoneNumber = "",
            businessTypes = emptyList(),
            companyAddress = CompanyAddress.NULL_COMPANY_ADDRESS,
            coordinate = Coordinate.NULL_COORDINATE,
            serviceArea = 0
        )
    }
}