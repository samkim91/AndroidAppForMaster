package kr.co.soogong.master.data.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.estimation.ImagePath

@Parcelize
data class Review(
    val recommendation: Int?,
    val kindness: Int,
    val quality: Int,
    val affordability: Int,
    val punctuality: Int,
    val projectType: String,
    val reviewContent: String,
    val imageList: List<ImagePath>,
    val createdAt: String,
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): Review {
            val item = jsonObject.get("data").asJsonObject
            val attributes = item.get("attributes").asJsonObject

            val imagesJson = attributes.getAsJsonArray("images")
            val images = mutableListOf<ImagePath>()
            for (item in imagesJson) {
                images.add(ImagePath.fromJson(item.asJsonObject))
            }

            return Review(
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

        val NULL_REVIEW = Review(
            4,
            4,
            3,
            4,
            3,
            "욕실 위생도기 및 수전 설치/교체 시공",
            "너무 꼼꼼히 잘 해주셨어요. 다음에 또 부탁드리고 싶네요! 감사합니다.",
            listOf(ImagePath("")),
            "2020.07.01"
        )
    }
}