package kr.co.soogong.master.data.dto.requirement.review

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewScoreDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("scoreCode")
    val scoreCode: String,

    // TODO: 2022/01/10 이름을 바로 서버에서 보내주면 좋을 듯 싶다. 현재는 앱에서 컨버트하고 있음
    @SerializedName("scoreName")
    val scoreName: String? = null,

    @SerializedName("score")
    val score: Double? = 0.0,
) : Parcelable {
    companion object
}