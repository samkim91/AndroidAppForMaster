package kr.co.soogong.master.data.model.requirement

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImagePath(
    @SerializedName("image_path")
    val path: String?
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): ImagePath {
            return ImagePath(
                path = jsonObject.get("image_path").asString
            )
        }

        val TEST_IMAGE_PATH = ImagePath("https://s3.ap-northeast-2.amazonaws.com/assets.soogong.co.kr/kcqvz39srrfjx0wcrrcq7jf5baw4?response-content-disposition=inline%3B%20filename%3D%22flower.jpeg%22%3B%20filename%2A%3DUTF-8%27%27flower.jpeg&response-content-type=image%2Fjpeg&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIATLOO24V4DXZC2C7K%2F20210512%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Date=20210512T012720Z&X-Amz-Expires=300&X-Amz-SignedHeaders=host&X-Amz-Signature=cbf5c743fbd28b538b64dadee5a57816479f12a9f00af18c16d6a5d5b345866f")
        val NULL_IMAGE_PATH = ImagePath("")
    }
}