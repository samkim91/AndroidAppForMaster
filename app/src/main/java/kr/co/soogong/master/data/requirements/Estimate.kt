package kr.co.soogong.master.data.requirements

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Estimate(
    @SerializedName("price")
    val price: String?,

    @SerializedName("possible_date")
    val possibleDate: String?,

    @SerializedName("contents")
    val contents: String?
): Parcelable