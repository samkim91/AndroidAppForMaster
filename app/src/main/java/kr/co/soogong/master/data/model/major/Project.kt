package kr.co.soogong.master.data.model.major

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Project(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @Expose(serialize = false, deserialize = false)
    var checked: Boolean = false
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): Project {
            val id = jsonObject.get("id").asInt
            val name = jsonObject.get("name").asString
            return Project(id, name)
        }

        val TEST_PROJECT = Project(
            1, "아파트 새시 제작설치/교체", false
        )

        val NULL_PROJECT = Project(
            0, "", false
        )
    }
}