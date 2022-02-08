package kr.co.soogong.master.data.entity.preferences

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "Notice")
data class NoticeDto(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("section")
    val section: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("hitCount")
    val hitCount: Int,

    @SerializedName("isNew")
    val isNew: Boolean?,

    @SerializedName("createdBy")
    val createdBy: String,

    @SerializedName("createdAt")
    val createdAt: Date,

    @SerializedName("updatedBy")
    val updatedBy: String,

    @SerializedName("updatedAt")
    val updatedAt: Date,
) : Parcelable {
    companion object
}