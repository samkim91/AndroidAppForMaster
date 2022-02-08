package kr.co.soogong.master.data.entity.requirement.estimationTemplate

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class EstimationTemplateDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("masterId")
    val masterId: Int?,

    @SerializedName("description")
    val description: String,

    @SerializedName("createdAt")
    val createdAt: Date? = null,

    @SerializedName("updatedAt")
    val updatedAt: Date? = null,
) : Parcelable {
    companion object {
        fun addMasterId(estimationTemplateDto: EstimationTemplateDto, masterId: Int) =
            EstimationTemplateDto(
                id = estimationTemplateDto.id,
                masterId = masterId,
                description = estimationTemplateDto.description,
            )
    }
}