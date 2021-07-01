package kr.co.soogong.master.data.model.major

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.profile.MajorDto

@Parcelize
data class Project(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @Expose(serialize = false, deserialize = false)
    var checked: Boolean = false
) : Parcelable {
    companion object {
        fun fromMajorDto(majorDtos: List<MajorDto>?): MutableList<Project> {
            return majorDtos?.map {
                Project(
                    id = it.id,
                    name = it.name,
                    checked = true,
                )
            }!!.toMutableList()
        }
    }
}