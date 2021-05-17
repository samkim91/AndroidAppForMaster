package kr.co.soogong.master.data.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.category.BusinessType
import kr.co.soogong.master.data.estimation.ImagePath

@Parcelize
data class RequiredInformation(
    val briefIntroduction: String,
    val representativeImages: List<ImagePath>,
    val businessUnitInformation: BusinessUnitInformation,
    val warrantyPeriod: String,
    val warrantyDescription: String,
    val career: String,
    val businessRepresentativeName: String,
    val phoneNumber: String,
    val businessType: List<BusinessType>,
    val address: String,
    val subAddress: String,
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): RequiredInformation {
            val item = jsonObject.get("data").asJsonObject
            val attributes = item.get("attributes").asJsonObject

            return RequiredInformation(
                briefIntroduction = attributes.get("brief_introduction").asString,
                representativeImages = attributes.get("representative_images").asJsonArray.map {
                    ImagePath.fromJson(
                        it.asJsonObject
                    )
                },
                businessRepresentativeName = attributes.get("business_representative_name").asString,
                businessUnitInformation = BusinessUnitInformation.fromJson(attributes.get("business_unit_information").asJsonObject),
                warrantyPeriod = attributes.get("warranty_period").asString,
                warrantyDescription = attributes.get("warranty_description").asString,
                career = attributes.get("career").asString,
                phoneNumber = attributes.get("phone_number").asString,
                businessType = attributes.get("business_type").asJsonArray.map {
                    BusinessType.fromJson(
                        it.asJsonObject
                    )
                },
                address = attributes.get("address").asString,
                subAddress = attributes.get("subAddress").asString,
            )
        }

        val TEST_REQUIRED_INFORMATION = RequiredInformation(
            briefIntroduction = "강남지역 30년 역사의 싱크대 장인! 믿고 맡게 주세요! 강남지역 30년 역사의 싱크대 장인! 믿고 맡게 주세요! 강남지역 30년 역사의 싱크대 장인! 믿고 맡게 주세요!",
            representativeImages = listOf(ImagePath.TEST_IMAGE_PATH, ImagePath.TEST_IMAGE_PATH, ImagePath.TEST_IMAGE_PATH),
            businessRepresentativeName = "성현식",
            businessUnitInformation = BusinessUnitInformation.TEST_BUSINESS_UNIT_INFORMATION_2,
            warrantyPeriod = "2년",
            warrantyDescription = "2년 동안 이것저것 고쳐줌. 2년 동안 이것저것 고쳐줌. 2년 동안 이것저것 고쳐줌. 2년 동안 이것저것 고쳐줌.",
            career = "2년",
            phoneNumber = "010 - 3290 -1234",
            businessType = listOf(BusinessType.TEST_BUSINESS_TYPE, BusinessType.TEST_BUSINESS_TYPE),
            address = "서울특별시 동작구",
            subAddress = "머시기 상가 303호",
        )

        val NULL_REQUIRED_INFORMATION = RequiredInformation(
            briefIntroduction = "",
            representativeImages = emptyList(),
            businessRepresentativeName = "",
            businessUnitInformation = BusinessUnitInformation.NULL_BUSINESS_UNIT_INFORMATION,
            warrantyPeriod = "",
            warrantyDescription = "",
            career = "",
            phoneNumber = "",
            businessType = emptyList(),
            address = "",
            subAddress = "",
        )
    }
}