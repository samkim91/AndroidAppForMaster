package kr.co.soogong.master.data.user

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kr.co.soogong.master.ext.getNullable

@Parcelize
@Entity(tableName = "User")
data class User(
    @PrimaryKey
    @SerializedName("keycode")
    val keycode: String,

    @SerializedName("using_plan")
    val usingPlan: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("detail_address")
    val detailAddress: String,

    @SerializedName("average_star_count")
    val starCount: Double,

    @SerializedName("reviews_count")
    val reviewsCount: Int,

    @SerializedName("categories")
    val categories: List<String>,

    @SerializedName("description")
    val description: String,

    @SerializedName("introduction")
    val introduction: String?,

    @SerializedName("name")
    val name: String,

    @SerializedName("positions")
    val positions: List<String>
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): User {
            val item = jsonObject.get("attributes").asJsonObject
            return User(
                keycode = item.get("keycode").asString,
                usingPlan = item.get("using_plan").asString,
                address = item.get("address").asString,
                detailAddress = item.get("detail_address").asString,
                starCount = item.get("average_star_count").asDouble,
                categories = item.get("categories").asJsonArray.map { it.asString },
                description = item.get("description").asString,
                introduction = item.getNullable("introduction")?.asString,
                name = item.get("name").asString,
                reviewsCount = item.get("reviews_count").asInt,
                positions = item.get("positions").asJsonArray.map { it.asString }
            )
        }
    }
}