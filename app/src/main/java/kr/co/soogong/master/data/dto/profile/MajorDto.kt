package kr.co.soogong.master.data.dto.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MajorDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("soogongmanYn")
    val soogongmanYn: Boolean,

    @SerializedName("useYn")
    val useYn: Boolean,

    @SerializedName("createdAt")
    val createdAt: String?,

    @SerializedName("updatedAt")
    val updatedAt: String?,

    @SerializedName("categoryId")
    val categoryId: Int?,

    @SerializedName("categoryName")
    val categoryName: String?,

    @SerializedName("categoryNameEn")
    val categoryNameEn: String?,

    // TODO: 2021/06/18 변경된 프로퍼티로 뷰 바인드 하는거 작업해줘야함..
) : Parcelable {
    companion object {

    }
}