package kr.co.soogong.master.data.model.requirement

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EstimationMessage(
    @SerializedName("price")
    val totalPrice: String?,

    @SerializedName("personnel")
    val personnel: String?,

    @SerializedName("material")
    val material: String?,

    @SerializedName("trip")
    val trip: String?,

    @SerializedName("description")
    val description: String?
) : Parcelable