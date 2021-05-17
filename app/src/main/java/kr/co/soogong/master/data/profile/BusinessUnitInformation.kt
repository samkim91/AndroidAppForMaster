package kr.co.soogong.master.data.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.estimation.ImagePath

@Parcelize
data class BusinessUnitInformation(
    val businessUnitType: String,       // individual, legal, freelancer
    val businessUnitName: String,
    val identicalNumber: Int,
    val identicalImage: ImagePath?,
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): BusinessUnitInformation {
            val item = jsonObject.get("data").asJsonObject

            return BusinessUnitInformation(
                businessUnitType = item.get("businessUnitType").asString,
                businessUnitName = item.get("businessUnitName").asString,
                identicalNumber = item.get("identicalNumber").asInt,
                identicalImage = ImagePath(item.get("identicalImage").asString),
            )
        }

        val TEST_BUSINESS_UNIT_INFORMATION = BusinessUnitInformation(
            businessUnitType = "법인사업자",
            businessUnitName = "수공",
            identicalNumber = 1234567890,
            identicalImage = ImagePath.TEST_IMAGE_PATH,
        )

        val TEST_BUSINESS_UNIT_INFORMATION_2 = BusinessUnitInformation(
            businessUnitType = "프리랜서",
            businessUnitName = "",
            identicalNumber = 20020616,
            identicalImage = ImagePath.NULL_IMAGE_PATH,
        )

        val NULL_BUSINESS_UNIT_INFORMATION = BusinessUnitInformation(
            "", "",0, ImagePath.NULL_IMAGE_PATH
        )
    }
}