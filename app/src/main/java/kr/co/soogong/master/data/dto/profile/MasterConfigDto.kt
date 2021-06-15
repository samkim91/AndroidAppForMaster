package kr.co.soogong.master.data.dto.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class MasterConfigDto(
    val id: Int,
    val groupCode: String,
    val groupName: String,
    val code: String,
    val name: String,
    val value: String?,
    val createdAt: Date?,
    val updatedAt: Date?,
) : Parcelable {
    companion object {

    }
}