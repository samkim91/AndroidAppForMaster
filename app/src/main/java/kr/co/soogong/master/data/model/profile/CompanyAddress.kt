package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompanyAddress(
    val roadAddress: String?,
    val detailAddress: String?,
) : Parcelable{
    companion object {
        val TEST_COMPANY_ADDRESS = CompanyAddress("서울 서초구 바우뫼로 213", "4층")
        val NULL_COMPANY_ADDRESS = CompanyAddress("", "")
    }
}