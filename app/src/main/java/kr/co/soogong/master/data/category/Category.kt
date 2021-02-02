package kr.co.soogong.master.data.category

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int,
    val name: String
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): Category {
            val id = jsonObject.get("id").asInt
            val name = jsonObject.get("attributes").asJsonObject.get("name").asString
            return Category(id, name)
        }
    }
}