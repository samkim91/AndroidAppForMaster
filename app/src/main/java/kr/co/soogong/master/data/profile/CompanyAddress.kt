package kr.co.soogong.master.data.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompanyAddress(
    val mainAddress: String,
    val subAddress: String,
) : Parcelable{
    companion object {
        fun fromJson(jsonObject: JsonObject): CompanyAddress {
            val item = jsonObject.get("data").asJsonObject
            val attributes = item.get("attributes").asJsonObject
            return CompanyAddress(
                mainAddress = attributes.get("mainAddress").asString,
                subAddress = attributes.get("subAddress").asString,
            )
        }

        val TEST_COMPANY_ADDRESS = CompanyAddress("서울 서초구 바우뫼로 213", "4층")
        val NULL_COMPANY_ADDRESS = CompanyAddress("", "")
    }
}