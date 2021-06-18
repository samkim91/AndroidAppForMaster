package kr.co.soogong.master.data.dto.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConfigCodeDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("configGroupCode")
    val configGroupCode: String,

    @SerializedName("groupName")
    val groupName: String,

    @SerializedName("code")
    val code: String,

    @SerializedName("name")  // TODO: 2021/06/18 한글명 나와줘야함..
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String,
) : Parcelable {
    companion object {

    }
}