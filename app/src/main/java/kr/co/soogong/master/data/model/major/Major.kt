package kr.co.soogong.master.data.model.major

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.profile.MajorDto
import kr.co.soogong.master.data.dto.profile.MasterDto

@Parcelize
data class Major(
    @SerializedName("category")
    val category: Category?,

    @SerializedName("projects")
    val projects: MutableList<Project>?,
) : Parcelable {
    companion object {
        fun fromMasterDto(masterDto: MasterDto): List<Major>? {
            return masterDto.majors?.groupBy { majorDto ->
                majorDto.categoryId
            }?.map { groupedMajors ->
                Major(
                    category = Category.fromMajorDto(groupedMajors.value.find { majorDto ->
                        majorDto.categoryId == groupedMajors.key }),
                    projects = Project.fromMajorDto(groupedMajors.value)
                )
            }
        }
    }
}
