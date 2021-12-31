package kr.co.soogong.master.data.dto.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MasterConfigDto(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("configGroupCode")
    val groupCode: String? = null,

    @SerializedName("configGroupName")
    val groupName: String? = null,

    @SerializedName("configCode")
    val code: String? = null,

    @SerializedName("configName")
    val name: String? = null,

    @SerializedName("value")
    val value: String? = null,
) : Parcelable {
    companion object
}