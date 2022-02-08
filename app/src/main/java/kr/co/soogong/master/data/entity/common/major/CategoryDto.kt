package kr.co.soogong.master.data.entity.common.major

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("nameEn")
    val nameEn: String?,
) : Parcelable {
    companion object
}