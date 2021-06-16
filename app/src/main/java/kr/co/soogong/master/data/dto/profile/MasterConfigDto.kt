package kr.co.soogong.master.data.dto.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class MasterConfigDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("configGroupCode")
    val configGroupCode: String,

    @SerializedName("code")
    val code: ConfigCodeDto,

    @SerializedName("value")
    val value: String,

    @SerializedName("createdAt")
    val createdAt: Date,

    @SerializedName("updatedAt")
    val updatedAt: Date,
) : Parcelable {
    companion object {

    }
}