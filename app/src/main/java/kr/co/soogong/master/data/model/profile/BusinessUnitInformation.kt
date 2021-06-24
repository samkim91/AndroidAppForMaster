package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.AttachmentDto

@Parcelize
data class BusinessUnitInformation(
    val businessType: String?,       // individual, legal, freelancer
    val businessName: String?,       // 상호명
    val shopName: String?,        // 업체명
    val businessNumber: String?,
    val businessRegistImage: AttachmentDto?,
) : Parcelable {
    companion object {
        val TEST_BUSINESS_UNIT_INFORMATION = BusinessUnitInformation(
            businessType = "법인사업자",
            businessName = "주식회사 수공",
            shopName = "수공",
            businessNumber = "1234567890",
            businessRegistImage = null,
        )

        val TEST_BUSINESS_UNIT_INFORMATION_2 = BusinessUnitInformation(
            businessType = "프리랜서",
            businessName = "",
            shopName = "",
            businessNumber = "20020616",
            businessRegistImage = null,
        )

        val NULL_BUSINESS_UNIT_INFORMATION = BusinessUnitInformation(
            "", "", "", "", null
        )
    }
}