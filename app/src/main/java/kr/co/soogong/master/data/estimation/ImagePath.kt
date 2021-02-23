package kr.co.soogong.master.data.estimation

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImagePath(
    @SerializedName("image_path")
    val path: String
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): ImagePath {
            return ImagePath(
                path = jsonObject.get("image_path").asString
            )
        }
    }
}