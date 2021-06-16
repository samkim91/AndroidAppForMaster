package kr.co.soogong.master.data.model.major

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.profile.ProjectDto

@Parcelize
data class Major(
    @SerializedName("category")
    val category: Category?,

    @SerializedName("projects")
    val projects: MutableList<Project>?,
) : Parcelable {
    companion object {



//        fun fromProjectDto(list: List<ProjectDto>): List<Major> {
//            val ret = mutableListOf<Major>()
//
//            list.map { projectDto ->
//                if (ret.count { major -> major.category?.name == projectDto.category.name } > 0) {
//                    ret.find { major -> major.category?.name == projectDto.category.name }?.projects?.add(
//                        Project(
//                            id = projectDto.id,
//                            name = projectDto.name,
//                            checked = true
//                        )
//                    )
//                } else {
//                    ret.add(
//                        Major(
//                            category = Category(
//                                id = projectDto.category.id,
//                                name = projectDto.category.name,
//                            ),
//                            projects = mutableListOf(Project())
//                        )
//                    )
//                }
//
//            }
//        }


        fun fromJson(jsonObject: JsonObject): Major {
            val item = jsonObject.get("business_type").asJsonObject
            val projectsArray = item.get("project").asJsonArray

            return Major(
                category = Category.fromJson(item.get("category").asJsonObject),
                projects = MutableList(projectsArray.size()) {
                    Project.fromJson(projectsArray.get(it).asJsonObject)
                }
            )
        }

        val TEST_BUSINESS_TYPE = Major(
            Category.TEST_CATEGORY,
            mutableListOf(Project.TEST_PROJECT, Project.TEST_PROJECT, Project.TEST_PROJECT)
        )

        val NULL_BUSINESS_TYPE = Major(
            Category.NULL_CATEGORY,
            mutableListOf()
        )
    }
}
