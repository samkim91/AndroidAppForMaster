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
    val configGroupCode: String?,

    @SerializedName("configGroupName")
    val configGroupName: String?,

    @SerializedName("code")
    val code: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("value")
    val value: String?,
) : Parcelable {
    companion object {

    }
}