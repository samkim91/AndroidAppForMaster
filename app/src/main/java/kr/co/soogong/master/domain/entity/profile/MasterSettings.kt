package kr.co.soogong.master.domain.entity.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.entity.profile.MasterSettingsDto

@Parcelize
data class MasterSettings(
    val id: Int,
    val uid: String,
    val approvedStatus: String,
    val requestMeasureYn: Boolean,
) : Parcelable {
    companion object {
        fun fromDto(masterSettingsDto: MasterSettingsDto) =
            MasterSettings(
                id = masterSettingsDto.id,
                uid = masterSettingsDto.uid,
                approvedStatus = masterSettingsDto.approvedStatus,
                requestMeasureYn = masterSettingsDto.requestMeasureYn,
            )
    }
}