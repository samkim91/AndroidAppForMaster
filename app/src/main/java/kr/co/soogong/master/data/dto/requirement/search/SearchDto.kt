package kr.co.soogong.master.data.dto.requirement.search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class SearchDto(
    @SerializedName("search")
    val searchingText: String,

    @SerializedName("interval")
    val searchingPeriod: Date,
) : Parcelable {
    companion object
}