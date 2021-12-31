package kr.co.soogong.master.data.dto.requirement.qna

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RequirementQnaDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("question")
    val question: String?,

    @SerializedName("answer")
    val answer: String?,
) : Parcelable {
    companion object
}