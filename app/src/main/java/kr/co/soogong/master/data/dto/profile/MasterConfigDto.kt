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
    val groupCode: String?,

    @SerializedName("configGroupName")
    val groupName: String?,

    @SerializedName("configCode")
    val code: String?,

    @SerializedName("configName")
    val name: String?,

    @SerializedName("value")
    val value: String?,
) : Parcelable {
    companion object {

    }
}