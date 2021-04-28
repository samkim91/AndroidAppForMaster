package kr.co.soogong.master.data.category

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusinessType(
    val category: Category?,
    val projects: List<Project>,
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): BusinessType {
            val item = jsonObject.get("business_type").asJsonObject
            val projectsArray = item.get("project").asJsonArray

            return BusinessType(
                category = Category.fromJson(item.get("category").asJsonObject),
                projects = List(projectsArray.size()) {
                    Project.fromJson(projectsArray.get(it).asJsonObject)
                }
            )
        }
    }
}
