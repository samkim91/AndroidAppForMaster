package kr.co.soogong.master.data.profile

import android.net.Uri
import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.estimation.ImagePath

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

        val TEST_BUSINESS_UNIT_INFORMATION = BusinessUnitInformation(
            businessUnit = "개인사업자",
            identicalNumber = 1234567890,
            imageForUpload = Uri.EMPTY,
            imageForShow = ImagePath.TEST_IMAGE_PATH,
        )

        val NULL_BUSINESS_UNIT_INFORMATION = BusinessUnitInformation(
            "", 0, Uri.EMPTY, ImagePath.NULL_IMAGE_PATH
        )
    }
}