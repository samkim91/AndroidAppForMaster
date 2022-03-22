package kr.co.soogong.master.domain.entity.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MasterConfig(
    val id: Int? = null,
    val groupCode: String? = null,
    val groupName: String? = null,
    val code: String? = null,
    val name: String? = null,
    val value: String? = null,
) : Parcelable {
    companion object
}