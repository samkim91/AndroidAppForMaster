package kr.co.soogong.master.data.profile

import android.net.Uri
import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.category.BusinessType
import kr.co.soogong.master.data.estimation.ImagePath

@Parcelize
data class RequiredInformation(
    val briefIntroduction: String,
    val companyImages: List<ImagePath>,
    val businessRepresentativeName: String,
    val businessUnitInformation: BusinessUnitInformation,
    val warrantyPeriod: String,
    val warrantyDescription: String,
    val career: String,
    val phoneNumber: String,
    val address: String,
    val subAddress: String,
    val businessType: List<BusinessType>,
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): RequiredInformation {
            val item = jsonObject.get("data").asJsonObject
            val attributes = item.get("attributes").asJsonObject

            val imagesJson = attributes.getAsJsonArray("company_images")
            val images = mutableListOf<ImagePath>()
            for(image in imagesJson){
                images.add(ImagePath.fromJson(image.asJsonObject))
            }

            val businessTypesJson = attributes.get("business_type").asJsonArray
            val businessTypes = mutableListOf<BusinessType>()
            for(businessType in businessTypesJson){
                businessTypes.add(BusinessType.fromJson(businessType.asJsonObject))
            }

            return RequiredInformation(
                briefIntroduction = attributes.get("brief_introduction").asString,
                companyImages = images,
                businessRepresentativeName = attributes.get("business_representative_name").asString,
                businessUnitInformation = BusinessUnitInformation.fromJson(attributes.get("business_unit_information").asJsonObject),
                warrantyPeriod = attributes.get("warranty_period").asString,
                warrantyDescription = attributes.get("warranty_description").asString,
                career = attributes.get("career").asString,
                phoneNumber = attributes.get("phone_number").asString,
                address = attributes.get("address").asString,
                subAddress = attributes.get("sub_address").asString,
                businessType = businessTypes,
                )
        }

        val NULL_REQUIRED_INFORMATION = RequiredInformation(
            briefIntroduction = "강남지역 30년 역사의 싱크대 장인! 믿고 맡게 주세요! 강남지역 30년 역사의 싱크대 장인! 믿고 맡게 주세요! 강남지역 30년 역사의 싱크대 장인! 믿고 맡게 주세요!",
            companyImages = listOf(ImagePath.NULL_IMAGE_PATH, ImagePath.NULL_IMAGE_PATH),
            businessRepresentativeName = "성현식",
            businessUnitInformation = BusinessUnitInformation.NULL_BUSINESS_UNIT_INFORMATION,
            warrantyPeriod = "2년",
            warrantyDescription = "2년 동안 이것저것 고쳐줌. 2년 동안 이것저것 고쳐줌. 2년 동안 이것저것 고쳐줌. 2년 동안 이것저것 고쳐줌.",
            career = "2년",
            phoneNumber = "010 - 3290 -1234",
            address = "서울특별시 동작구",
            subAddress = "머시기 상가 303호",
            businessType = listOf(BusinessType.NULL_BUSINESS_TYPE, BusinessType.NULL_BUSINESS_TYPE),
            )
    }
}

@Parcelize
data class BusinessUnitInformation(
    val businessUnit: String,       // individual, legal, freelancer
    val identicalNumber: Int,
    val imageForUpload: Uri?,
    val imageForShow: ImagePath?,
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): BusinessUnitInformation {
            val item = jsonObject.get("data").asJsonObject

            return BusinessUnitInformation(
                businessUnit = item.get("business_unit").asString,
                identicalNumber = item.get("identical_number").asInt,
                imageForUpload = Uri.EMPTY,
                imageForShow = ImagePath.fromJson(item.get("image_for_show").asJsonObject)
            )
        }

        val NULL_BUSINESS_UNIT_INFORMATION = BusinessUnitInformation(
            businessUnit = "개인사업자",
            identicalNumber = 1234567890,
            imageForUpload = Uri.EMPTY,
            imageForShow = ImagePath.NULL_IMAGE_PATH,
        )
    }
}