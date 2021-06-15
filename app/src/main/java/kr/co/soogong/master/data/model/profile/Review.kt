package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.model.requirement.ImagePath

@Parcelize
data class Review(
    val id: Int?,
    val recommendation: Int?,
    val kindness: Int,
    val quality: Int,
    val affordability: Int,
    val punctuality: Int,
    val projectType: String,
    val reviewContent: String,
    val imageList: ArrayList<ImagePath>,
    val createdAt: String,
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): Review {
            val item = jsonObject.get("data").asJsonObject
            val attributes = item.get("attributes").asJsonObject

            val imagesJson = attributes.getAsJsonArray("images")
            val images = arrayListOf<ImagePath>()
            for (image in imagesJson) {
                images.add(ImagePath.fromJson(image.asJsonObject))
            }

            return Review(
                id = attributes.get("id").asInt,
                recommendation = attributes.get("recommendation").asInt,
                kindness = attributes.get("kindness").asInt,
                quality = attributes.get("quality").asInt,
                affordability = attributes.get("affordability").asInt,
                punctuality = attributes.get("punctuality").asInt,
                projectType = attributes.get("project_type").asString,
                reviewContent = attributes.get("review_content").asString,
                imageList = images,
                createdAt = attributes.get("created_at").asString,
            )
        }

        val TEST_REVIEW = Review(
            1,
            4,
            4,
            3,
            4,
            3,
            "욕실 위생도기 및 수전 설치/교체 시공",
            "너무 꼼꼼히 잘 해주셨어요. 다음에 또 부탁드리고 싶네요! 감사합니다.",
            arrayListOf(
                ImagePath.TEST_IMAGE_PATH,
                ImagePath.TEST_IMAGE_PATH,
                ),
            "2020.07.01"
        )
    }
}