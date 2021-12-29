package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.common.AttachmentDto

@Parcelize
data class BusinessUnitInformation(
    val businessType: String?,       // sole, corporate, freelancer
    val businessName: String?,       // 상호명
    val shopName: String?,        // 업체명
    val businessNumber: String?,
    val businessRegistImage: AttachmentDto?,
) : Parcelable {
    companion object {

    }
}