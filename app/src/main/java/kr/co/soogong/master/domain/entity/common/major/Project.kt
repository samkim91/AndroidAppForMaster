package kr.co.soogong.master.domain.entity.common.major

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.entity.common.major.ProjectDto

@Parcelize
data class Project(
    val id: Int,
    val name: String,
) : Parcelable {
    companion object {
        val defaultData : Project = Project(Int.MIN_VALUE, "")

        fun fromDtos(projectDtos: List<ProjectDto>?) =
            projectDtos?.map { projectDto ->
                fromDto(projectDto)
            }

        fun fromDto(projectDto: ProjectDto): Project =
            Project(
                id = projectDto.id,
                name = projectDto.name
            )
    }
}