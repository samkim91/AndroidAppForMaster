package kr.co.soogong.master.data.requirements

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Estimate(
    @SerializedName("price_in_number")
    val priceInNumber: String?,

//    @SerializedName("personnel")
//    val personnel: String?,

    @SerializedName("contents")
    val contents: String?
) : Parcelable