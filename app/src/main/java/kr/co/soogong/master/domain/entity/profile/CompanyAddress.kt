package kr.co.soogong.master.domain.entity.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompanyAddress(
    val roadAddress: String?,
    val detailAddress: String,
) : Parcelable{
    companion object
}