package kr.co.soogong.master.data.user

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.util.extension.getNullable

@Parcelize
@Entity(tableName = "User")
data class User(
    @PrimaryKey
    @SerializedName("keycode")
    val keycode: String,

    @SerializedName("master_name")
    val masterName: String?,

    @SerializedName("name")
    val name: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("detail_address")
    val detailAddress: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("average_star_count")
    val starCount: Double,

    @SerializedName("reviews_count")
    val reviewsCount: Int,

    @SerializedName("categories_to_arr")
    val categories: List<String>,

    @SerializedName("projects_to_arr")
    val projects: List<String>,

    @SerializedName("introduction")
    val introduction: String?
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): User {
            val item = jsonObject.get("data").asJsonObject
            return User(
                keycode = item.get("keycode").asString,
                masterName = item.getNullable("master_name")?.asString,
                name = item.get("name").asString,
                address = item.get("address").asString,
                detailAddress = item.get("detail_address").asString,
                description = item.get("description").asString,
                starCount = item.get("average_star_count").asDouble,
                reviewsCount = item.get("reviews_count").asInt,
                categories = item.get("categories_to_arr").asJsonArray.map { it.asString },
                projects = item.get("projects_to_arr").asJsonArray.map { it.asString },
                introduction = item.getNullable("introduction")?.asString
            )
        }
    }
}