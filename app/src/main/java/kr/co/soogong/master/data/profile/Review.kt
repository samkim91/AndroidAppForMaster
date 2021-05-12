package kr.co.soogong.master.data.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.estimation.ImagePath

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

        val NULL_REVIEW = Review(
            1,
            4,
            4,
            3,
            4,
            3,
            "욕실 위생도기 및 수전 설치/교체 시공",
            "너무 꼼꼼히 잘 해주셨어요. 다음에 또 부탁드리고 싶네요! 감사합니다.",
            arrayListOf(
                ImagePath("https://s3.ap-northeast-2.amazonaws.com/assets.soogong.co.kr/kcqvz39srrfjx0wcrrcq7jf5baw4?response-content-disposition=inline%3B%20filename%3D%22flower.jpeg%22%3B%20filename%2A%3DUTF-8%27%27flower.jpeg&response-content-type=image%2Fjpeg&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIATLOO24V4DXZC2C7K%2F20210512%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Date=20210512T012720Z&X-Amz-Expires=300&X-Amz-SignedHeaders=host&X-Amz-Signature=cbf5c743fbd28b538b64dadee5a57816479f12a9f00af18c16d6a5d5b345866f"),
                ImagePath("https://s3.ap-northeast-2.amazonaws.com/assets.soogong.co.kr/93f41s5vk7wevusjb9b7vdlik4w4?response-content-disposition=inline%3B%20filename%3D%22cat.jpeg%22%3B%20filename%2A%3DUTF-8%27%27cat.jpeg&response-content-type=image%2Fjpeg&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIATLOO24V4DXZC2C7K%2F20210512%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Date=20210512T012720Z&X-Amz-Expires=300&X-Amz-SignedHeaders=host&X-Amz-Signature=154d39700db98ced27468da047c6440d3ed4f48b3fa9a2f6a1e39447e6a5a990"),
                ),
            "2020.07.01"
        )
    }
}