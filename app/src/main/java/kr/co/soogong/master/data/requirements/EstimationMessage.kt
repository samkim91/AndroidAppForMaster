package kr.co.soogong.master.data.requirements

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EstimationMessage(
    @SerializedName("price_in_number")
    val priceInNumber: String?,

    @SerializedName("personnel")
    val personnel: String?,

    @SerializedName("material")
    val material: String?,

    @SerializedName("trip")
    val trip: String?,

    @SerializedName("message")
    val message: String?
) : Parcelable