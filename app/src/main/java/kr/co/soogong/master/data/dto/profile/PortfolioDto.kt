package kr.co.soogong.master.data.dto.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.model.profile.Portfolio
import java.util.*

@Parcelize
data class PortfolioDto(
    val id: Int,
    val title: String,
    val description: String,
    val project: String?,
    val type: String,
    val beforeRepairImageId: String,
    val afterRepairImageId: String,
    val price: String,
    val createdAt: Date?,
    val updatedAt: Date?,
) : Parcelable {
    companion object {

    }
}