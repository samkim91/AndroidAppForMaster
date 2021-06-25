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

    }
}