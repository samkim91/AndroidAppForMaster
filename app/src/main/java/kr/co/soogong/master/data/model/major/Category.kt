package kr.co.soogong.master.data.model.major

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): Category {
            val id = jsonObject.get("id").asInt
            val name = jsonObject.get("name").asString
            return Category(id, name)
        }

        val TEST_CATEGORY = Category(1, "새시")

        val NULL_CATEGORY = Category(0, "")
    }
}