package kr.co.soogong.master.data.entity.profile

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class MasterSettingsDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("approvedStatus")
    val approvedStatus: String,

    @SerializedName("requestMeasureYn")
    val requestMeasureYn: Boolean,
) : Parcelable {
    companion object
}