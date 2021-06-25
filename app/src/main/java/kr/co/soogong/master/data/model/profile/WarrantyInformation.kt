package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class WarrantyInformation(
    val warrantyPeriod: Int?,
    val warrantyDescription: String?,
) : Parcelable{
    companion object {

    }
}