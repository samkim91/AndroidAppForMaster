package kr.co.soogong.master.data.model.major

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.profile.MajorDto

@Parcelize
data class Category(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,
) : Parcelable {
    companion object {
        fun fromMajorDto(majorDto: MajorDto?): Category {
            return Category(
                id = majorDto?.categoryId,
                name = majorDto?.categoryName,
            )
        }
    }
}