package kr.co.soogong.master.data.dto.requirement.review

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewScoreDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("scoreCode")
    val scoreCode: String?,

    @SerializedName("scoreName")
    val scoreName: String?,

    @SerializedName("score")
    val score: Float?,
) : Parcelable {
    companion object {

    }
}