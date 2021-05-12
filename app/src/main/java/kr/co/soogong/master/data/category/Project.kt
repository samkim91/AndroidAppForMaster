package kr.co.soogong.master.data.category

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class Project(
    val id: Int,
    val name: String,
    var checked: Boolean = false
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): Project {
            val id = jsonObject.get("id").asInt
            val name = jsonObject.get("attributes").asJsonObject.get("name").asString
            return Project(id, name)
        }

        val NULL_PROJECT = Project(
            1, "아파트 새시 제작설치/교체", false
        )
    }
}