package kr.co.soogong.master.domain.entity.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WarrantyInformation(
    val warrantyPeriod: Int?,
    val warrantyDescription: String?,
) : Parcelable {
    companion object
}