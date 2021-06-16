package kr.co.soogong.master.data.dto.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.model.profile.Portfolio
import java.util.*

@Parcelize
data class PortfolioDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("project")
    val project: String?,

    @SerializedName("type")
    val type: String,

    @SerializedName("beforeRepairImageId")
    val beforeRepairImageId: String,

    @SerializedName("afterRepairImageId")
    val afterRepairImageId: String,

    @SerializedName("price")
    val price: String,

    @SerializedName("createdAt")
    val createdAt: Date?,

    @SerializedName("updatedAt")
    val updatedAt: Date?,
) : Parcelable {
    companion object {

    }
}