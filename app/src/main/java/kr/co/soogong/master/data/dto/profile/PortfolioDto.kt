package kr.co.soogong.master.data.dto.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PortfolioDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("project")
    val project: String? = null,

    @SerializedName("type")
    val type: String,

    @SerializedName("beforeRepairImageId")
    val beforeRepairImageId: String? = null,

    @SerializedName("afterRepairImageId")
    val afterRepairImageId: String? = null,

    @SerializedName("price")
    val price: Int? = null,
) : Parcelable {
    companion object {

    }
}