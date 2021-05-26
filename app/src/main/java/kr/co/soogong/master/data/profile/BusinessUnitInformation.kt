package kr.co.soogong.master.data.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.estimation.ImagePath

@Parcelize
data class BusinessUnitInformation(
    val businessUnitType: String,       // individual, legal, freelancer
    val businessName: String,       // 상호명
    val companyName: String,        // 업체명
    val identicalNumber: Int,
    val identicalImage: ImagePath?,
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): BusinessUnitInformation {
            val item = jsonObject.get("data").asJsonObject

            return BusinessUnitInformation(
                businessUnitType = item.get("businessUnitType").asString,
                businessName = item.get("businessName").asString,
                companyName = item.get("companyName").asString,
                identicalNumber = item.get("identicalNumber").asInt,
                identicalImage = ImagePath(item.get("identicalImage").asString),
            )
        }

        val TEST_BUSINESS_UNIT_INFORMATION = BusinessUnitInformation(
            businessUnitType = "법인사업자",
            businessName = "주식회사 수공",
            companyName = "수공",
            identicalNumber = 1234567890,
            identicalImage = ImagePath.TEST_IMAGE_PATH,
        )

        val TEST_BUSINESS_UNIT_INFORMATION_2 = BusinessUnitInformation(
            businessUnitType = "프리랜서",
            businessName = "",
            companyName = "",
            identicalNumber = 20020616,
            identicalImage = ImagePath.NULL_IMAGE_PATH,
        )

        val NULL_BUSINESS_UNIT_INFORMATION = BusinessUnitInformation(
            "", "", "", 0, ImagePath.NULL_IMAGE_PATH
        )
    }
}