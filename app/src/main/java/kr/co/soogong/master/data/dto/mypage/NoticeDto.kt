package kr.co.soogong.master.data.dto.mypage

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class NoticeDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("typeCode")
    val typeCode: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("hitCount")
    val hitCount: Int,

    @SerializedName("author")
    val author: String,

    @SerializedName("createdAt")
    val createdAt: Date,

    @SerializedName("updatedAt")
    val updatedAt: Date,
) : Parcelable {
    companion object {

    }
}