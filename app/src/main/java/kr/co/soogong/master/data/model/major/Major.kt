package kr.co.soogong.master.data.model.major

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Major(
    @SerializedName("category")
    val category: Category?,

    @SerializedName("projects")
    val projects: MutableList<Project>?,
) : Parcelable {
    companion object {
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
