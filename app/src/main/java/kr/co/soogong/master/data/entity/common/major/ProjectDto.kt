package kr.co.soogong.master.data.entity.common.major

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.domain.entity.common.major.Project

@Parcelize
data class ProjectDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,
) : Parcelable {
    companion object {
        fun fromProjects(projects: List<Project>?) =
            projects?.map { project ->
                ProjectDto(
                    id = project.id,
                    name = project.name
                )
            }
    }
}