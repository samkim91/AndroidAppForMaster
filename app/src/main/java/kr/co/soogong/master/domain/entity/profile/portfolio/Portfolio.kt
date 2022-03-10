package kr.co.soogong.master.domain.entity.profile.portfolio

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Portfolio(
    val id: Int,
    val masterId: Int,
    val title: String,
    val description: String?,
    val project: String?,
    val type: PortfolioType,
    val beforeImageUrl: String?,
    val afterImageUrl: String?,
    val repairPhoto: List<String>?,
    val price: Int?,
) : Parcelable {
    companion object
}