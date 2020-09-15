package kr.co.soogong.master.data.user

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kr.co.soogong.master.data.rawtype.user.RawUser

@Parcelize
@Entity(tableName = "User")
data class User(
    @PrimaryKey
    @SerializedName("keycode")
    val keycode: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("average_star_count")
    val starCount: Double,

    @SerializedName("reviews_count")
    val reviewsCount: Int,

    @SerializedName("categories")
    val categories: List<String>,

    @SerializedName("description")
    val description: String,

    @SerializedName("introduction")
    val introduction: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("positions")
    val positions: List<String>
) : Parcelable {
    companion object {
        fun from(rawUser: RawUser): User = User(
            keycode = rawUser.attributes.keycode,
            address = rawUser.attributes.address,
            starCount = rawUser.attributes.average_star_count,
            categories = rawUser.attributes.categories,
            description = rawUser.attributes.description,
            introduction = rawUser.attributes.introduction,
            name = rawUser.attributes.name,
            reviewsCount = rawUser.attributes.reviews_count,
            positions = rawUser.attributes.positions
        )
    }
}