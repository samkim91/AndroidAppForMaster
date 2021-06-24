package kr.co.soogong.master.data.model.major

import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.profile.MajorDto

@Parcelize
data class Major(
    @SerializedName("category")
    val category: Category?,

    @SerializedName("projects")
    val projects: MutableList<Project>?,
) : Parcelable {
    companion object {
        // TODO: 2021/06/18 converter need...
//        fun fromMajorDto(majorDto: MajorDto): Major {
//
//        }

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
