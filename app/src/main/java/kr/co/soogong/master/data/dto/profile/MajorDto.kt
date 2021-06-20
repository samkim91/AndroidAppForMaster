package kr.co.soogong.master.data.dto.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.model.major.Major

@Parcelize
data class MajorDto(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("soogongmanYn")
    val soogongmanYn: Boolean? = null,

    @SerializedName("useYn")
    val useYn: Boolean? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("updatedAt")
    val updatedAt: String? = null,

    @SerializedName("categoryId")
    val categoryId: Int? = null,

    @SerializedName("categoryName")
    val categoryName: String? = null,

    @SerializedName("categoryNameEn")
    val categoryNameEn: String? = null,

    // TODO: 2021/06/18 변경된 프로퍼티로 뷰 바인드 하는거 작업해줘야함..
) : Parcelable {
    companion object {
        fun fromMajorList(majors: List<Major>?): List<MajorDto> {
            val ret = mutableListOf<MajorDto>()

            majors?.map { major ->
                major.projects?.map { project ->
                    val majorDto = MajorDto(
                        id = project.id,
                        name = project.name,
                        categoryId = major.category?.id,
                        categoryName = major.category?.name
                    )

                    ret.add(majorDto)
                }
            }

            return ret
        }
    }
}